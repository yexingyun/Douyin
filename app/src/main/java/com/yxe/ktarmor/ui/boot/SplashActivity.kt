package com.yxe.ktarmor.ui.boot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.yxe.ktarmor.R
import com.yxe.ktarmor.ui.account.LoginActivity
import com.yxe.mvvm.utils.L
import com.yxe.mvvm.utils.SPUtils
import com.yxe.mvvm.utils.TimeConstants
import com.yxe.mvvm.utils.TimeUtils

class SplashActivity : AppCompatActivity() {
    //    var handler2: Handler = Handler(Handler.Callback {
//        if (it.what==1){
//
//        }
//        false
//    })
    var handler: Handler = Handler {
        if (it.what == 1) {
            startMainActivity()
        }
        false
    }

    fun startMainActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.sendEmptyMessageDelayed(1, 1000)
        val userid = SPUtils.getInstance().getString("userid")
        val lastLoginTime = SPUtils.getInstance().getLong(userid,0)
        if (TextUtils.isEmpty(userid)||isOver15Day(lastLoginTime)) {
          handler.removeMessages(1)
          StartLoginActivity()
        }
    }

    private fun isOver15Day(lastLoginTime: Long?): Boolean {
        if (lastLoginTime != null) {
            val timeSpan = TimeUtils.getTimeSpan(lastLoginTime, System.currentTimeMillis(), TimeConstants.DAY)
            L.d("差距d::::" + timeSpan + "")
            if (timeSpan > 15) {
                return true
            } else {
                return false
            }
        }
        return true

    }

    private fun StartLoginActivity() {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null);
    }
}
