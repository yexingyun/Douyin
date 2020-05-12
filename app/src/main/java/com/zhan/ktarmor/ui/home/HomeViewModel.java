package com.zhan.ktarmor.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zhan.ktarmor.account.data.AccountRepository;
import com.zhan.ktarmor.account.data.response.HomeBean;
import com.zhan.ktarmor.account.data.response.HomeRsp;
import com.zhan.ktarmor.account.data.response.LoginRsp;
import com.zhan.ktarmor.account.data.response.UploadBean;
import com.zhan.ktarmor.common.data.BaseResponse;
import com.zhan.mvvm.mvvm.BaseViewModel;

import java.io.File;
import java.util.List;

public class HomeViewModel extends BaseViewModel<HomeRepository> {

    private MutableLiveData<List<HomeBean.DataBean>> mText;
    private MutableLiveData<UploadBean.DataBean> upLoadLive;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    }

    public LiveData<List<HomeBean.DataBean>> getText() {
        return mText;
    }
    public void getData(){
        HomeBean homeData = getRepository().HomeData();
        if (homeData!=null&&homeData.getCode()==0){
            mText.postValue(homeData.getData());
        }else {
            showError(homeData.getMsg());
        }
    }

    public void upLoadFile(String userid ,
                           String videoTitle ,
                           int type,
                           File file){
        UploadBean uploadBean = getRepository().uploadFile(userid,videoTitle,type,file);
        if (uploadBean!=null&&uploadBean.getCode()==0){
            upLoadLive.postValue(uploadBean.getData());
        }else {
            showError(uploadBean.getMsg());
        }
    }
}