package com.yxe.ktarmor.ui.account.data

import com.yxe.ktarmor.ui.account.data.response.EmptyRsp

import com.yxe.ktarmor.common.api.ApiRepository
import com.yxe.ktarmor.common.data.BaseResponse
import com.yxe.ktarmor.ui.account.data.response.LoginRsp

/**
 * @author  hyzhan
 * @date    2019/5/23
 * @desc    TODO
 */
class AccountRepository : ApiRepository() {

    suspend fun login(account: String, password: String): BaseResponse<LoginRsp> {

        // TODO local DB

        // network
        return launchIO { apiService.login(account, password) }
    }

    suspend fun collect(id: Int):BaseResponse<EmptyRsp> {
        return launchIO { apiService.collectAsync(id) }
    }

    suspend fun register(account: String, password: String, phone: String): BaseResponse<LoginRsp> {
        return launchIO { apiService.register(account, password,phone) }

    }
}