package com.zhan.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseFragment : Fragment(), CoroutineScope by MainScope() {
    open var root:View ?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(getLayoutId(), container, false);
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBefore()
        initView()
        initListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    open fun initBefore() {}

    open fun initView() {}

    open fun initListener() {}

    open fun initData() {}
}