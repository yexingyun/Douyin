package com.yxe.ktarmor.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.hbzhou.open.flowcamera.CustomCameraView;
//import com.hbzhou.open.flowcamera.listener.ClickListener;
//import com.hbzhou.open.flowcamera.listener.FlowCameraListener;
//import com.otaliastudios.cameraview.controls.Hdr;
//import com.otaliastudios.cameraview.controls.WhiteBalance;
import com.yxe.mvvm.mvvm.LifecycleFragment;
import com.yxe.mvvm.utils.ThreadPoolProxyFactory;
import com.yxe.ktarmor.ui.camera.CameraActivity;
import com.yxe.ktarmor.ui.camera.OpenCVActivity;
import com.yxe.ktarmor.ui.select.VideoSelectActivity;
import com.yxe.ktarmor.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class HomeFragment extends LifecycleFragment<HomeViewModel> implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    String url = "http://47.96.224.106:8082/images/111.mp4";
//String url = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private List<String> urls = new ArrayList<>();
    private AdapterRecyclerViewTiny adapterVideoList;


    @Override
    public void onViewCreated(@NotNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
//        root = inflater.inflate(, container, false);
        recyclerView = getRoot().findViewById(R.id.recyclerview);
        FloatingActionButton fab = getRoot().findViewById(R.id.fab);
        fab.setOnClickListener(this);
        homeViewModel.getText().observe(this, s -> {
            Log.e(TAG, "---------------------" + s.toString());

//                for (HomeBean.DataBean dataBean : s){
//                    urls.add(API.BASE_URL+dataBean.getUrl());
//                }
            adapterVideoList.setUrls(s);
//                Log.e(TAG, "---------------------" + s.get(0).toString());
        });
        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                homeViewModel.getData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterVideoList = new AdapterRecyclerViewTiny(getContext());
        recyclerView.setAdapter(adapterVideoList);

    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {//这个的本质是gotoThisJzvd，不管他是不是原来的容器
                //如果这个容器中jzvd的url是currentJzvd的url，那么直接回到这里，不用管其他的
                JZVideoPlayerStandard jzvd = view.findViewById(R.id.videoplayer);
//                JZVideoPlayerStandard currentJzvd = (JZVideoPlayerStandard) Jzvd.CURRENT_JZVD;
//                if (jzvd != null && currentJzvd != null &&
//                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())
//                        && Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PLAYING) {
//                    ViewGroup vp = (ViewGroup) jzvd.getParent();
//                    vp.removeAllViews();
//                    ((ViewGroup) currentJzvd.getParent()).removeView(currentJzvd);
//                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    vp.addView(currentJzvd, lp);
//                    currentJzvd.setScreenNormal();
//                    Jzvd.CONTAINER_LIST.pop();
//                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayerStandard jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd != null ) {
                    if (jzvd.currentState==JZVideoPlayerStandard.CURRENT_STATE_PLAYING) {
                        jzvd.release();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard)root.findViewById(R.id.videoplayer);
//
//        jzVideoPlayerStandard.setUp(url,
//                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");

//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }


    @Override
    public void onPause() { //选择适当的声明周期释放
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
//        startActivity(new Intent(getContext(), CameraXXActivity.class));
        ActionSheetDialog();
    }
    private void ActionSheetDialog() {
        final String[] stringItems = {"打开相机", "图库选择","opencv"};
        final ActionSheetDialog dialog = new ActionSheetDialog(getContext(), stringItems, null);
        dialog.title("")//
                .isTitleShow(false)
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL((parent, view, position, id) -> {
            if (stringItems[position].equals("打开相机")){
                startActivity(new Intent(getContext(), CameraActivity.class));
            }else if (stringItems[position].equals("图库选择")){
                startActivity(new Intent(getContext(), VideoSelectActivity.class));
            }else if (stringItems[position].equals("opencv")){
                startActivity(new Intent(getContext(), OpenCVActivity.class));
            }
            dialog.dismiss();
        });
    }
}