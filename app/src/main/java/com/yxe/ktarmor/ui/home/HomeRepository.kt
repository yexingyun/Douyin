package com.yxe.ktarmor.ui.home

import com.yxe.ktarmor.ui.account.data.response.HomeBean
import com.yxe.ktarmor.ui.account.data.response.UploadBean
import com.yxe.ktarmor.common.api.ApiRepository
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