package com.yxe.ktarmor.empty

import com.yxe.ktarmor.R
import com.yxe.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_empty.*

/**
 * @author  hyzhan
 * @date    2019/5/22
 * @desc    TODO
 */
class EmptyActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_empty

    override fun initView() {

        mEmptyView.emptyText = "我是空"
        mEmptyView.errorText = "我是 Error"

        btnLoading.setOnClickListener {
            mEmptyView.triggerLoading()
        }

        btnEmpty.setOnClickListener {
            mEmptyView.triggerEmpty()
        }

        btnError.setOnClickListener {
            mEmptyView.triggerNetError()
        }

        btnOk.setOnClickListener {
            mEmptyView.triggerOk()
        }
    }
}