package com.zhan.ktarmor

import android.app.Application
import android.util.Log
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
//import androidx.camera.core.CameraXConfig
import com.zhan.ktarmor.common.MyRetrofitConfig
import com.zhan.ktarmor.utils.AppUtils
import com.zhan.mvvm.KtArmor
import iknow.android.utils.BaseUtils
import nl.bravobit.ffmpeg.FFmpeg


/**
 * @author  hyzhan
 * @date    2019/5/28
 * @desc    TODO
 */
//
class BaseApplication: Application(), CameraXConfig.Provider{

    override fun onCreate() {
        super.onCreate()

        // 初始化KtArmor
        KtArmor.init(this, MyRetrofitConfig())
        BaseUtils.init(this)
        AppUtils.init(this)
        initFFmpegBinary(this)
    }

    private fun initFFmpegBinary(baseApplication: BaseApplication) {
        if (!FFmpeg.getInstance(baseApplication).isSupported) {
            Log.e("ZApplication", "Android cup arch not supported!")
        }
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}
