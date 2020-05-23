package com.yxe.ktarmor.ui.camera;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yxe.mvvm.base.BaseActivity;
import com.yxe.ktarmor.R;
import com.yxe.mvvm.base.BaseActivity;

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
    public   void cover(){
        File file = new File("/storage/sdcard0/361");
        String fileNamepath = "";
        if (file.exists()){
             fileNamepath = file.getAbsolutePath();
        }else {
            file.mkdir();
        }
        Glide.with(OpenCVActivity.this)
                .load(file)
                .into(mImageview);
//        Mat srcImage = Imgcodecs.imread(fileNamepath);
//        if (srcImage.empty()){
//            return;
//        }
//        Mat dstImage = new Mat();
//        Imgproc.cvtColor(srcImage, dstImage, Imgproc.COLOR_BGR2GRAY,0);
//        Imgcodecs.imwrite("/storage/sdcard0/360/test.jpg", dstImage);
//        android里使用opencv读取照片
//        /storage/sdcard0/360/1588133063137.jpg
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = externalStorageDirectory +"/361";
        File f = new File(filename);
        if (!f.exists()){
            f.mkdir();
        }
        Mat src = Imgcodecs.imread(filename);
        if(src.empty()) return;

//android里使用opencv保存照片
        Mat srcGray = new Mat ();
        Imgproc.cvtColor(src, srcGray,Imgproc.COLOR_BGR2GRAY);
        String filenamesrcGray = externalStorageDirectory +"/image/gray.jpg";
        Imgcodecs.imwrite(filenamesrcGray, srcGray);
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

//        解决  前置摄像头旋转显示问题
        Core.flip(mRgba, mRgba, 1); //旋转
        Core.flip(mGray, mGray, 1);

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
