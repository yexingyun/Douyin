package com.zhan.ktarmor.ui.home

import com.zhan.ktarmor.account.data.response.*
import com.zhan.ktarmor.common.api.ApiRepository
import com.zhan.ktarmor.common.data.BaseResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @author  hyzhan
 * @date    2019/5/23
 * @desc    TODO
 */
class HomeRepository : ApiRepository() {

     fun HomeData(): HomeBean {

        // TODO local DB

        // network
        return apiService.homeData(1).execute().body()!!
    }
    fun uploadFile(userid: String,
                   videoTitle: String,
                   type: Int,
                 file: File): UploadBean {

        var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        var body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        // network
        return apiService.uploadFile(userid,videoTitle,
                type,
                body).execute().body()!!
    }

//     fun collect(id: Int):BaseResponse<EmptyRsp> {
//        return launchIO { apiService.collectAsync(id) }
//    }
}