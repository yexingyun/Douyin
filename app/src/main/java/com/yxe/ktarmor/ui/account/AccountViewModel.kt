

package com.zhan.ktarmor.account

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.yxe.ktarmor.R
import com.yxe.ktarmor.ui.account.data.AccountRepository
import com.yxe.ktarmor.ui.account.data.response.EmptyRsp
import com.yxe.ktarmor.ui.account.data.response.LoginRsp
import com.yxe.mvvm.bean.SharedData
import com.yxe.mvvm.bean.SharedType
import com.yxe.mvvm.mvvm.BaseViewModel
import com.yxe.mvvm.utils.SPUtils


/**
 * @author  hyzhan
 * @date    2019/5/23
 * @desc    TODO
 */
class AccountViewModel : BaseViewModel<AccountRepository>() {

    val loginData = MutableLiveData<LoginRsp>()
    val registerData = MutableLiveData<LoginRsp>()
    val collectData = MutableLiveData<EmptyRsp>()

    fun login(account: String, password: String) {

        if (check(account, password)) return



        quickLaunch<LoginRsp> {

            onStart { showLoading() }

            request { repository.login(account, password) }

            onSuccess {
                SPUtils.getInstance().put(it?.userid.toString(), it?.updateTime!!)
                SPUtils.getInstance().put("userid",   it?.userid!!)

                loginData.value = it
            }
            onFail {
             showError(it.toString())
            }
        }
    }

    private fun check(vararg args:String): Boolean {
        for ( str in args){
            if (TextUtils.isEmpty(str)) {
                sharedData.value = SharedData(strRes = R.string.account_or_password_empty, type = SharedType.RESOURCE)
                return true
            }
        }
        return false
    }

    fun collect() {
        launchUI({
            showLoading()
            repository.collect(9014).execute({
                collectData.value = it
            })
        })
    }

    fun register(account: String, password: String, phone: String) {
        if (check(account, password,phone)) return
        quickLaunch<LoginRsp> {
            onStart { showLoading() }

            request { repository.register(account, password,phone) }

            onSuccess {
                registerData.value = it
            }
            onFail {
                showError(it.toString())
            }
        }
    }
}

