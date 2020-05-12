package com.zhan.ktarmor.account.data.response

/**
 * @author  hyzhan
 * @date    2019/5/28
 * @desc    TODO
 */


data class HomeRsp(
    val createTime: Long,
    val filename: String,
    val id: Int,
    val playNum: Any,
    val type: Int,
    val updateTime: Long,
    val url: String,
    val userid: String,
    val videoCoverImgUrl: Any,
    val videoTitle: String,
    val zanNum: Any
)