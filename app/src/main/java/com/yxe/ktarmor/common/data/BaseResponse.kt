package com.yxe.ktarmor.common.data

import com.yxe.mvvm.bean.KResponse

/**
 * @author  hyzhan
 * @date    2019/5/28
 * @desc    TODO
 */
data class BaseResponse<T>(var data: T?,
                           var code: Int = -1,
                           var msg: String = "") : KResponse<T> {

    override fun isSuccess(): Boolean = code == 0

    override fun getKData(): T? = data

    override fun getKMessage(): String? = msg
}
