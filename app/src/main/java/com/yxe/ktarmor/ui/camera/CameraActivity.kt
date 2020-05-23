package com.yxe.ktarmor.ui.camera

// Your IDE likely can auto-import these classes, but there are several
// different implementations so we list them here to disambiguate.
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.TextureView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.hbzhou.open.flowcamera.CaptureImageButton.Companion.BUTTON_STATE_BOTH
import com.hbzhou.open.flowcamera.CaptureImageButton.Companion.BUTTON_STATE_ONLY_RECORDER
import com.hbzhou.open.flowcamera.CustomCameraView
import com.hbzhou.open.flowcamera.listener.FlowCameraListener
import com.otaliastudios.cameraview.controls.Hdr
import com.otaliastudios.cameraview.controls.WhiteBalance
import com.yxe.ktarmor.R
import com.yxe.ktarmor.ui.home.HomeViewModel
import com.yxe.mvvm.mvvm.LifecycleActivity
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.THRESH_BINARY
import java.io.File
import java.util.concurrent.Executors

// This is an arbitrary number we are using to keep track of the permission
// request. Where an app has multiple context for requesting permission,
// this can help differentiate the different contexts.
private const val REQUEST_CODE_PERMISSIONS = 10

// This is an array of all the permission specified in the manifest.
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

class CameraActivity : LifecycleActivity<HomeViewModel>() {
    init {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

//        viewFinder = findViewById(R.id.view_finder)
//
        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
//
//        // Every time the provided texture view changes, recompute layout
//        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
//            updateTransform()
//        }

//
    }


    override fun onResume() {
        super.onResume()


    }
    override fun getLayoutId(): Int {
        return R.layout.activity_camera
    }

    private fun showLong(absolutePath: String?) {
        Toast.makeText(this, absolutePath, Toast.LENGTH_LONG).show()
        Log.e("path==", absolutePath)
    }
    // Add this after onCreate

    private lateinit var homeViewModel:HomeViewModel
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView

    private fun startCamera() {
        val flowCamera = findViewById<CustomCameraView>(R.id.flowCamera)
        // 绑定生命周期 您就不用关心Camera的开启和关闭了 不绑定无法预览
        flowCamera.setBindToLifecycle(this)
        // 设置白平衡模式
        flowCamera.setWhiteBalance(WhiteBalance.AUTO)
        // 设置只支持单独拍照拍视频还是都支持
//         BUTTON_STATE_ONLY_CAPTURE  BUTTON_STATE_ONLY_RECORDER  BUTTON_STATE_BOTH
        flowCamera.setCaptureMode(BUTTON_STATE_ONLY_RECORDER)
        // 开启HDR
        flowCamera.setHdrEnable(Hdr.ON)
        // 设置最大可拍摄小视频时长
        flowCamera.setRecordVideoMaxTime(20)
        // 设置拍照或拍视频回调监听
        flowCamera.setFlowCameraListener(object : FlowCameraListener {
            // 录制完成视频文件返回
            override fun recordSuccess(file: File) {
              showLong(file.absolutePath)
                finish()
            }
            // 操作拍照或录视频出错
            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {

            }
            // 拍照返回
            override fun captureSuccess(file: File) {
                val src = Imgcodecs.imread(file.absolutePath)
                if (src.empty()) return

//android里使用opencv保存照片
                //android里使用opencv保存照片
                val srcGray = Mat()
//                val absolutePath = getApplicationContext().getFilesDir().getAbsolutePath();
                val filename: String = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
                Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY)
                val filenamesrcGray: String = filename + "/gray.jpg"
//                val filenamesrcGray: String = absolutePath + "/gray.jpg"
                val imgFile = File(filenamesrcGray);
                if (!imgFile.exists()){
                    imgFile.createNewFile()
                }
                val imwrite = Imgcodecs.imwrite(filenamesrcGray, srcGray)
                if (imwrite){
                    Toast.makeText(this@CameraActivity,"写入成功",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@CameraActivity,"写入失败",Toast.LENGTH_SHORT).show()

                }
                val result = Mat()
                Imgproc.threshold(srcGray, result, 10.0, 1.0, THRESH_BINARY);
                Imgcodecs.imwrite(filenamesrcGray, result)
//                startActivity(Intent(this@CameraActivity,PhotoViewActivity::class.java).putExtra("path", "/image/gray.jpg"))
//                ToastUtils.showLong(file.absolutePath)
                // 最后通知图库更新
//               sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$`filenamesrcGray`")))

//                finish()
            }
        })
        //左边按钮点击事件
        flowCamera.setLeftClickListener {
            finish()
        }
//        val previewConfig = PreviewConfig.Builder().build()
//        val preview = Preview(previewConfig)
//
//        preview.setOnPreviewOutputUpdateListener {
//            previewOutput: Preview.PreviewOutput? ->
//            // Your code here. For example, use previewOutput?.getSurfaceTexture()
//            // and post to a GL renderer.
//        }
//
//        // Add this before CameraX.bindToLifecycle
//
//        // Create configuration object for the image capture use case
//        val imageCaptureConfig = ImageCaptureConfig.Builder()
//                .apply {
//                    // We don't set a resolution for image capture; instead, we
//                    // select a capture mode which will infer the appropriate
//                    // resolution based on aspect ration and requested mode
//                    setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
//                }.build()
//
//        // Build the image capture use case and attach button click listener
//        val imageCapture = ImageCapture(imageCaptureConfig)
//        findViewById<ImageButton>(R.id.capture_button).setOnClickListener {
//            val file = File(externalMediaDirs.first(),
//                    "${System.currentTimeMillis()}.jpg")
//
//            imageCapture.takePicture(file, executor,
//                    object : ImageCapture.OnImageSavedListener {
//                        override fun onError(
//                                imageCaptureError: ImageCapture.ImageCaptureError,
//                                message: String,
//                                exc: Throwable?
//                        ) {
//                            val msg = "Photo capture failed: $message"
//                            Log.e("CameraXApp", msg, exc)
//                            viewFinder.post {
//                                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        override fun onImageSaved(file: File) {
//                            val msg = "Photo capture succeeded: ${file.absolutePath}"
//                            Log.d("CameraXApp", msg)
//                            viewFinder.post {
//                                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    })
//        }
//
//
//        CameraX.bindToLifecycle(this as LifecycleOwner, preview)
    }

//    private fun updateTransform() {
//        // TODO: Implement camera viewfinder transformations
//        val matrix = Matrix()
//
//        // Compute the center of the view finder
//        val centerX = viewFinder.width / 2f
//        val centerY = viewFinder.height / 2f
//
//        // Correct preview output to account for display rotation
//        val rotationDegrees = when (viewFinder.display.rotation) {
//            Surface.ROTATION_0 -> 0
//            Surface.ROTATION_90 -> 90
//            Surface.ROTATION_180 -> 180
//            Surface.ROTATION_270 -> 270
//            else -> return
//        }
//        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
//
//        // Finally, apply transformations to our TextureView
//        viewFinder.setTransform(matrix)
//    }

    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}
