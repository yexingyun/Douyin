package com.yxe.ktarmor.ui.account

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.yxe.ktarmor.R
import com.yxe.ktarmor.ui.boot.MainActivity
import com.yxe.mvvm.mvvm.LifecycleActivity
import com.zhan.ktarmor.account.AccountViewModel
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.ktwing.ext.str
import kotlinx.android.synthetic.main.activity_login.*


/**
 * @author  hyzhan
 * @date    2019/5/22
 * @desc    TODO
 */
class LoginActivity : LifecycleActivity<AccountViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.setNavigationBarColor(Color.TRANSPARENT)
    }

    val LOGIN: Int = 1
    val REGISTER: Int = 0
    var state = LOGIN
    override fun initView() {

        mBtnLogin.setOnClickListener {
            if (state==LOGIN){
                viewModel.login(mEtAccount.str(), mEtPassword.str())
            }else{
                viewModel.register(mEtAccount.str(), mEtPassword.str(),mEtUsername.str())
            }
        }
        mBtnRegister.setOnClickListener {
            if (state==LOGIN){
                mTilUsername.visibility = View.VISIBLE
                state = REGISTER
                mBtnRegister.setText("登录")
                mBtnLogin.setText("注册")
            }else{
                mTilUsername.visibility = View.GONE
                state = LOGIN
                mBtnRegister.setText("注册")
                mBtnLogin.setText("登录")
            }
        }
        mBtnCollect.setOnClickListener {
            start2Activity(MainActivity().javaClass)
        }
    }

    override fun dataObserver() {
        viewModel.loginData.observe(this, Observer {
            toast("登录成功")
            start2Activity(MainActivity().javaClass)
            hideLoading()
        })
        viewModel.registerData.observe(this, Observer {
            toast("注册成功,赶紧登录去吧")
            mBtnRegister.performClick()
            hideLoading()
        })

        viewModel.collectData.observe(this, Observer {
            toast("收藏成功")
            hideLoading()
        })
    }
}