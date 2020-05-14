package com.zhan.ktarmor.ui.trim;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.zhan.ktarmor.R;
import com.zhan.ktarmor.databinding.ActivityVideoTrimBinding;
import com.zhan.ktarmor.ui.Pulish.PulishActivity;
import com.zhan.mvvm.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Author：J.Chou
 * Date：  2016.08.01 2:23 PM
 * Email： who_know_me@163.com
 * Describe:
 */
@SuppressLint("Registered")
public class VideoTrimmerActivity extends BaseActivity implements VideoTrimListener {

    private static final String TAG = "jason";
    private static final String VIDEO_PATH_KEY = "video-file-path";
    private static final String COMPRESSED_VIDEO_FILE_NAME = "compress.mp4";
    public static final int VIDEO_TRIM_REQUEST_CODE = 0x001;
    private ActivityVideoTrimBinding mBinding;
    private ProgressDialog mProgressDialog;

    public static void call(FragmentActivity from, String videoPath) {
        if (!TextUtils.isEmpty(videoPath)) {
            Bundle bundle = new Bundle();
            bundle.putString(VIDEO_PATH_KEY, videoPath);
            Intent intent = new Intent(from, VideoTrimmerActivity.class);
            intent.putExtras(bundle);
            from.startActivityForResult(intent, VIDEO_TRIM_REQUEST_CODE);
        }
    }

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_trim);
        Bundle bd = getIntent().getExtras();
        String path = "";
        if (bd != null) path = bd.getString(VIDEO_PATH_KEY);
        if (mBinding.trimmerView != null) {
            mBinding.trimmerView.setOnTrimVideoListener(this);
            mBinding.trimmerView.initVideoByURI(Uri.parse(path));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.trimmerView.onVideoPause();
        mBinding.trimmerView.setRestoreState(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.trimmerView.onDestroy();
    }

    @Override
    public void onStartTrim() {
//        showToast(getResources().getString(R.string.trimming)+"");
        buildDialog(getResources().getString(R.string.trimming)).show();
    }

    @Override
    public void onFinishTrim(String in) {
        // 最后通知图库更新
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + in)));

        if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
        Log.e(TAG, "path=====" + in);
        Log.e(TAG, "defaultSDCardPath=====" + in);
        startActivity(new Intent(this, PulishActivity.class));
//        start
//        finish();
        //TODO: please handle your trimmed video url here!!!
        //String out = StorageUtil.getCacheDir() + File.separator + COMPRESSED_VIDEO_FILE_NAME;
        //buildDialog(getResources().getString(R.string.compressing)).show();
        //VideoCompressor.compress(this, in, out, new VideoCompressListener() {
        //  @Override public void onSuccess(String message) {
        //  }
        //
        //  @Override public void onFailure(String message) {
        //  }
        //
        //  @Override public void onFinish() {
        //    if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
        //    finish();
        //  }
        //});
    }

    @Override
    public void onCancel() {
        mBinding.trimmerView.onDestroy();
        finish();
    }

    private ProgressDialog buildDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "", msg);
        }
        mProgressDialog.setMessage(msg);
        return mProgressDialog;
    }

    @Override
    public int getLayoutId() {
//    return  R.layout.activity_video_trim;
        return 0;
    }
}
