package com.yxe.ktarmor.ui.account.data.response;

/**
 * @author  hyzhan
 * @date    2019/5/28
 * @desc    TODO
 */

data class LoginRsp(
    val createTime: Any,
    val id: Int,
    val password: String,
    val phone: String,
    val status: Int,
    val updateTime: Long,
    val userid: String,
    val username: String
)

