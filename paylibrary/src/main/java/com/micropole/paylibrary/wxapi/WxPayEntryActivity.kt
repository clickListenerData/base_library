package com.micropole.paylibrary.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.micropole.paylibrary.PayManager
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * @ClassName       WxPayEntryActivity
 * @Description     wx pay
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/15 16:28
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
abstract class WxPayEntryActivity : Activity(), IWXAPIEventHandler{
    private lateinit var api : IWXAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this,getWxAppID())
        api.handleIntent(intent,this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api.handleIntent(intent,this)
    }

    override fun onResp(resp: BaseResp?) {
        if (PayManager.callBack != null){
            when(resp?.errCode){
                0 -> PayManager.callBack?.onPaySuccess()
                -1 -> PayManager.callBack?.onPayFail("支付失败：" + resp.errStr)
                -2 -> PayManager.callBack?.onPayFail("支付取消：" + resp.errStr)
            }
        }
        finish()
    }

    override fun onReq(req: BaseReq?) {
    }

    abstract fun getWxAppID() : String
}