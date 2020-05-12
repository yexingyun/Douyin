package com.zhan.ktarmor.common.api

import com.zhan.ktarmor.account.data.response.*
import com.zhan.ktarmor.common.data.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

}