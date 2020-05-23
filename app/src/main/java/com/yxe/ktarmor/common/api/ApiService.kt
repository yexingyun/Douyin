package com.yxe.ktarmor.common.api

import com.yxe.ktarmor.common.data.BaseResponse
import com.yxe.ktarmor.ui.account.data.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author  hyzhan
 * @date    2019/5/28
 * @desc    TODO
 */
interface ApiService {

    @POST(API.LOGIN)
    suspend fun login(@Query("username") username: String,
                      @Query("password") password: String): BaseResponse<LoginRsp>

    @POST(API.COLLECT)
    suspend fun collectAsync(@Path("id") id: Int): BaseResponse<EmptyRsp>

    @GET(API.VIDEOS)
    fun homeData(@Query("type") type: Int): Call<HomeBean>

    @Multipart
    @POST(API.UPLOADFILE)
    fun uploadFile(
            @Part("userid") userid: String,
            @Part("videoTitle") videoTitle: String,
            @Part("type") type: Int,
            @Part file: MultipartBody.Part): Call<UploadBean>

    @POST(API.REGISTER)
    suspend fun register(@Query("username") account: String, @Query("password") password: String, @Query("phone") phone: String): BaseResponse<LoginRsp>

}