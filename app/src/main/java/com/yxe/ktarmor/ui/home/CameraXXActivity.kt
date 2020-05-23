//package com.zhan.ktarmor.ui.home
//
//import android.Manifest
//import android.annotation.TargetApi
//import android.content.pm.PackageManager
//import android.graphics.Matrix
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.Surface
//import android.view.TextureView
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.Toast
//import androidx.camera.core.*
//import androidx.camera.core.CameraX.getCameraControl
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.LifecycleOwner
//import com.yxe.ktarmor.R
//import java.io.File
//
//private const val REQUEST_CODE_PERMISSIONS = 10
//
//// This is an array of all the permission specified in the manifest.
//val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//class CameraXXActivity : AppCompatActivity() {
//    private lateinit var viewFinder: TextureView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_camera_xx)
//        viewFinder = findViewById(R.id.view_finder)
////        main2Executor = ContextCompat.getMainExecutor(this)
//        // Request camera permissions
//        if (allPermissionsGranted()) {
//            startCamera()
//        } else {
//            ActivityCompat.requestPermissions(
//                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
//        }
////
////        // Every time the provided texture view changes, recompute layout
//        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
//            updateTransform()
//        }
//
//    }
//    private var lensFacing = CameraX.LensFacing.BACK
//    @TargetApi(Build.VERSION_CODES.P)
//    private fun startCamera() {
//        val previewConfig = PreviewConfig.Builder().apply {
////            setTargetResolution(Size(1920, 1080))
////            setTargetAspectRatio(AspectRatio.RATIO_16_9)
//
//        }.build()
//
//
//        // Build the viewfinder use case
//        val preview = Preview(previewConfig)
//        // Every time the viewfinder is updated, recompute layout
//        preview.setOnPreviewOutputUpdateListener {
//
//            // To update the SurfaceTexture, we have to remove it and re-add it
//            val parent = viewFinder.parent as ViewGroup
//            parent.removeView(viewFinder)
//            parent.addView(viewFinder, 0)
//
//            viewFinder.surfaceTexture = it.surfaceTexture
//            updateTransform()
//        }
//
//        val imageCaptureConfig = ImageCaptureConfig.Builder()
//                .apply {
//                    // We don't set a resolution for image capture; instead, we
//                    // select a capture mode which will infer the appropriate
//                    // resolution based on aspect ration and requested mode
//                    setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
//                    setFlashMode(FlashMode.ON)
//
//                }.build()
////        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
////            // In our analysis, we care more about the latest image than analyzing *every* image
////            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
////            // Set initial target rotation, we will have to call this again if rotation changes
////            // during the lifecycle of this use case
//////            setTargetRotation(viewFinder.display.rotation)
////        }.build()
////
////        var imageAnalyzer = ImageAnalysis(analyzerConfig).apply {
////
////        }
//        // Build the image capture use case and attach button click listener
//        val imageCapture = ImageCapture(imageCaptureConfig)
//        findViewById<ImageButton>(R.id.capture_button).setOnClickListener {
//            val file = File("/mnt/internal_sd/DCIM/Camera/",
//                    "${System.currentTimeMillis()}.jepg")
//            val mainExecutor = ContextCompat.getMainExecutor(this)
//            imageCapture.takePicture(file, mainExecutor,
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
//
//        CameraX.bindToLifecycle(this as LifecycleOwner, preview,imageCapture)
////        getCameraControl(lensFacing).cancelFocusAndMetering()
//    }
//
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
//
//    /**
//     * Process result from permission request dialog box, has the request
//     * been granted? If yes, start Camera. Otherwise display a toast
//     */
//    override fun onRequestPermissionsResult(
//            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (allPermissionsGranted()) {
//                viewFinder.post { startCamera() }
//            } else {
//                Toast.makeText(this,
//                        "Permissions not granted by the user.",
//                        Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }
//    }
//
//    /**
//     * Check if all permission specified in the manifest have been granted
//     */
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(
//                baseContext, it) == PackageManager.PERMISSION_GRANTED
//    }
//}
