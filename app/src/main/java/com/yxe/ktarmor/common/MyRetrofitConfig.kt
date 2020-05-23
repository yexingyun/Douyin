package com.yxe.ktarmor.common

import com.yxe.ktarmor.common.api.API
import com.yxe.ktarmor.common.interceptor.CookieInterceptor
import com.yxe.mvvm.http.BaseRetrofitConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * author：  HyZhan
 * create：  2019/7/24
 * desc：    TODO
 */
class MyRetrofitConfig : BaseRetrofitConfig() {

    override val baseUrl: String
        get() = API.BASE_URL

    override fun initOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        return super.initOkHttpClient(CookieInterceptor.create())
    }
}