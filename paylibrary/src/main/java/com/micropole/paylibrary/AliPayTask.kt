package com.micropole.paylibrary

import android.app.Activity
import android.os.AsyncTask
import com.alipay.sdk.app.PayTask
import com.micropole.paylibrary.entity.AliPayResultEntity

/**
 * @ClassName       AliPayTask
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/15 17:40
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class AliPayTask(activity: Activity) : AsyncTask<String,Void,Map<String,String>>() {

    private var task : PayTask = PayTask(activity)

    override fun doInBackground(vararg params: String?): Map<String, String> {
        return task.payV2(params[0],true)
    }

    override fun onPostExecute(result: Map<String, String>?) {
        val status = AliPayResultEntity(result).resultStatus
        if (status == "9000"){
            PayManager.callBack?.onPaySuccess()
        }else{
            PayManager.callBack?.onPayFail("支付失败")
        }
    }
}