package com.yxe.ktarmor.ui.select;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.GridView;


import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yxe.mvvm.base.BaseActivity;
import com.yxe.ktarmor.R;
import com.yxe.mvvm.base.BaseActivity;

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
    private android.widget.GridView videoGridview;

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        videoGridview = (GridView) findViewById(R.id.video_gridview);
        mVideoLoadManager = new VideoLoadManager();
        mVideoLoadManager.setLoader(new VideoCursorLoader());
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



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_select;
    }
}
