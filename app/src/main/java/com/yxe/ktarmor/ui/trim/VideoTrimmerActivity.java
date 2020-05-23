package com.yxe.ktarmor.ui.trim;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.yxe.ktarmor.R;
import com.yxe.ktarmor.ui.Pulish.PulishActivity;
import com.yxe.ktarmor.view.VideoTrimmerView;
import com.yxe.mvvm.base.BaseActivity;
import com.yxe.mvvm.base.BaseActivity;

import org.jetbrains.annotations.Nullable;


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
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private ProgressDialog mProgressDialog;
    private VideoTrimmerView trimmerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 首先，检查是否拥有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                // 如果不需要解释，则开始请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

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
        Bundle bd = getIntent().getExtras();
        String path = "";
        trimmerView = findViewById(R.id.trimmer_view);
        if (bd != null) path = bd.getString(VIDEO_PATH_KEY);
        if (trimmerView != null) {
            trimmerView.setOnTrimVideoListener(this);
            trimmerView.initVideoByURI(Uri.parse(path));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        trimmerView.onVideoPause();
        trimmerView.setRestoreState(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trimmerView.onDestroy();
    }

    @Override
    public void onStartTrim() {
//        showToast(getResources().getString(R.string.trimming)+"");
        buildDialog(getResources().getString(R.string.trimming)).show();
    }

    @Override
    public void onFinishTrim(String in) {
        // 最后通知图库更新
//        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + in)));

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
        trimmerView.onDestroy();
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
        return R.layout.activity_video_trim;
//        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // 如果请求被取消，则结果数组为空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 许可被授予，耶！执行与联系人相关的任务。
                } else {
                    // 许可被拒绝，禁用使用此权限的功能。
                }
                return;
            }

            // 处理其他权限“case”
        }
    }
}
