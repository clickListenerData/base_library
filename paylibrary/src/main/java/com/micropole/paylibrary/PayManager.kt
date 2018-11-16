package com.micropole.paylibrary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.alipay.sdk.auth.AlipaySDK
import com.micropole.paylibrary.callback.PayResultCallBack
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import org.json.JSONObject
import kotlin.math.sign

/**
 * @ClassName       PayManager
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/15 16:46
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
object PayManager {

    const val PAY_WECHAT = 0X11
    const val PAY_ALI = 0X12

    var callBack : PayResultCallBack? = null
    var mWxApi : IWXAPI? = null

    fun initWxApi(context: Context,appID:String){
        mWxApi = WXAPIFactory.createWXAPI(context,appID)
        mWxApi?.registerApp(appID)
    }

    fun checkALIInstall(context : Context) : Boolean{
        val uri = Uri.parse("alipays://platformapi/startApp")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        return intent.resolveActivity(context.packageManager) != null
    }

    fun checkWXInstall() : Boolean{
        if (mWxApi == null){
            return false
        }
        return mWxApi!!.isWXAppInstalled && mWxApi!!.isWXAppSupportAPI
    }

    fun openWxPay(strInfo : String){
        if (!checkWXInstall()){
            callBack?.onPayFail("请先安装微信")
        }else if (mWxApi != null){
            mWxApi?.sendReq(getWxPayReq(strInfo))
        }else{
            callBack?.onPayFail("请先设置微信信息提供者")
        }
    }

    fun openAliPay(activity: Activity,strInfo : String){
        if (!checkALIInstall(activity)){
            callBack?.onPayFail("请先安装支付宝")
        }else{
            AliPayTask(activity).execute(strInfo)
        }
    }

    fun openAnyPay(activity: Activity,appID : String,type : Int,strInfo:String){
        if (type == PAY_ALI){
            openAliPay(activity,strInfo)
        }else if (type == PAY_WECHAT){
            if (mWxApi == null && appID.isNotEmpty()) initWxApi(activity,appID)
            openWxPay(strInfo)
        }
    }

    fun getWxPayReq(strInfo : String) : PayReq{
        val wx = JSONObject(strInfo)
        val payReq = PayReq()
        payReq.appId = wx.optString("appid")
        payReq.partnerId = wx.optString("mch_id")
        payReq.prepayId = wx.optString("prepay_id")
        payReq.nonceStr = wx.optString("nonce_str")
        payReq.timeStamp = wx.optString("time")
        payReq.packageValue = wx.optString("package", "Sign=WXPay")
        payReq.sign = wx.optString("sign")
        return payReq
    }

}