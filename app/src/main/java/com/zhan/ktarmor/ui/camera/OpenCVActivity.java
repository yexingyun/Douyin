package com.zhan.ktarmor.ui.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhan.ktarmor.R;
import com.zhan.ktarmor.ui.select.VideoSelectActivity;
import com.zhan.ktarmor.ui.select.VideoSelectAdapter;
import com.zhan.mvvm.base.BaseActivity;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import iknow.android.utils.callback.SimpleCallback;

public class OpenCVActivity extends BaseActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    public OpenCVActivity() {
        loadlib();
    }

    private CameraBridgeViewBase openCvCameraView;

    private static final String TAG = "OpencvActivity";
    private CascadeClassifier cascadeClassifier = null; //级联分类器
    private Mat mRgba; //图像容器
    private Mat mGray;
    private int absoluteFaceSize = 0;
    private Handler handler;
    private android.widget.ImageView mImageview;

    private void initializeOpenCVDependencies() {
        try {
            InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface_improved); //OpenCV的人脸模型文件： lbpcascade_frontalface_improved
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface_improved.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            // 加载cascadeClassifier
            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "Error loading cascade", e);
        }
        // 显示
        openCvCameraView.enableView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getpremisstiong();
        handler = new Handler();
        mImageview = findViewById(R.id.imageview);
        mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover();
            }
        });
    }
  public  static void loadlib(){

      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }
    public  static void cover(){
        Mat srcImage = Imgcodecs.imread("F:\\2625035632820481613.jpg");
        Mat dstImage = new Mat();
        Imgproc.cvtColor(srcImage, dstImage, Imgproc.COLOR_BGR2GRAY,0);
        Imgcodecs.imwrite("F:\\gray.jpg", dstImage);
    }


    private void getpremisstiong() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
            if (granted) { // Always true pre-M
                openCvCameraView = (CameraBridgeViewBase) findViewById(R.id.javaCameraView);
                openCvCameraView.setCameraIndex(0); //摄像头索引        -1/0：后置双摄     1：前置
                openCvCameraView.enableFpsMeter(); //显示FPS
                openCvCameraView.setCvCameraViewListener(this);
            } else {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.e(TAG, "OpenCV init error");
        }
        initializeOpenCVDependencies();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mGray = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba(); //RGBA
        mGray = inputFrame.gray(); //单通道灰度图

        if (absoluteFaceSize == 0) {
            int height = mGray.rows();
            if (Math.round(height * 0.2f) > 0) {
                absoluteFaceSize = Math.round(height * 0.2f);
            }
        }

        //解决  前置摄像头旋转显示问题
        //Core.flip(mRgba, mRgba, 1); //旋转
        //Core.flip(mGray, mGray, 1);

        //检测并显示
        MatOfRect faces = new MatOfRect();
        if (cascadeClassifier != null) {
            cascadeClassifier.detectMultiScale(mGray, faces, 1.1, 2, 2, new Size(absoluteFaceSize, absoluteFaceSize), new Size());
        }
        Rect[] facesArray = faces.toArray();
        if (facesArray.length > 0) {
            for (int i = 0; i < facesArray.length; i++) {    //用框标记
                Imgproc.rectangle(mRgba, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
            }
        }
        return mRgba;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_c_v;
    }
}
