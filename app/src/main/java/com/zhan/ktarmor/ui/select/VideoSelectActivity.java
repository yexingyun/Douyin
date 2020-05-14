package com.zhan.ktarmor.ui.select;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhan.ktarmor.R;
import com.zhan.mvvm.base.BaseActivity;

import iknow.android.utils.callback.SimpleCallback;

/**
 * Author：J.Chou
 * Date：  2016.08.01 2:23 PM
 * Email： who_know_me@163.com
 * Describe:
 */
public class VideoSelectActivity extends BaseActivity {

    private VideoSelectAdapter mVideoSelectAdapter;
    private VideoLoadManager mVideoLoadManager;
    //  private PreviewSurfaceView mSurfaceView;
    private ViewGroup mCameraSurfaceViewLy;
    private android.widget.RelativeLayout mTitleLayout;
    private android.widget.ImageView mBtnBack;
    private android.widget.GridView videoGridview;

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
//        mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
//        mBtnBack = (ImageView) findViewById(R.id.mBtnBack);
        videoGridview = (GridView) findViewById(R.id.video_gridview);
        mVideoLoadManager = new VideoLoadManager();
        mVideoLoadManager.setLoader(new VideoCursorLoader());
//    mBinding = DataBindingUtil.setContentView(this, );
        mCameraSurfaceViewLy = findViewById(R.id.layout_surface_view);
//        mBtnBack.setOnClickListener(this);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
            if (granted) { // Always true pre-M
                mVideoLoadManager.load(this, new SimpleCallback() {
                    @Override
                    public void success(Object obj) {
                        if (mVideoSelectAdapter == null) {
                            mVideoSelectAdapter = new VideoSelectAdapter(VideoSelectActivity.this, (Cursor) obj);
                        } else {
                            mVideoSelectAdapter.swapCursor((Cursor) obj);
                        }
                        if (videoGridview.getAdapter() == null) {
                            videoGridview.setAdapter(mVideoSelectAdapter);
                        }
                        mVideoSelectAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                finish();
            }
        });


    }

    private void initCameraPreview() {

    }

    private void hideOtherView() {
//    titleLayout.setVisibility(View.GONE);
//    videoGridview.setVisibility(View.GONE);
//    cameraPreviewLy.setVisibility(View.GONE);
    }

    private void resetHideOtherView() {
//    titleLayout.setVisibility(View.VISIBLE);
//    videoGridview.setVisibility(View.VISIBLE);
//    cameraPreviewLy.setVisibility(View.VISIBLE);
    }

//  private void addSurfaceView(PreviewSurfaceView surfaceView) {
//    mCameraSurfaceViewLy.addView(surfaceView);
//  }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == mBtnBack.getId()) {
//            finish();
//        }
//    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_select;
    }
}
