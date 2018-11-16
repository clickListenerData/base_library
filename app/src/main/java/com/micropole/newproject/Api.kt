package com.micropole.newproject

import com.micropole.baseapplibrary.appconst.AppApi
import com.micropole.baseapplibrary.appconst.Constants
import com.xx.baseutilslibrary.entity.BaseResponseEntity
import com.xx.baseutilslibrary.network.gson.XxGsonConverterFactory
import io.reactivex.Observable
import retrofit2.Call

/**
 * @ClassName       Api
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/16 9:24
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
object Api {

    var appService : Api.AppService? = null

    fun api() : AppService{
        if (appService == null){
            AppApi.setTimeOut()
            val map = HashMap<String,String>()
            map["lat"] = Constants.getLocation()[0]
            map["lng"] = Constants.getLocation()[1]
            AppApi.addHeader(map)
            AppApi.setIntercept(DoubleTokenIntercept())
            AppApi.setConverterFactory(XxGsonConverterFactory.create())
            appService = AppApi.Api()
        }
        return appService!!
    }

    interface AppService{
        fun refresh_token() : Call<BaseResponseEntity<String>>
    }
}