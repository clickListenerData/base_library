package com.micropole.paylibrary.callback

/**
 * @ClassName       PayResultCallBack
 * @Description     pay result
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/15 16:44
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
interface PayResultCallBack {

    fun onPaySuccess()

    fun onPayFail(error:String)
}