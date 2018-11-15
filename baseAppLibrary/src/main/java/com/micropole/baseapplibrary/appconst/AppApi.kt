package com.micropole.baseapplibrary.appconst

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.xx.baseutilslibrary.network.gson.XxGsonConverterFactory
import com.xx.baseutilslibrary.network.retrofit.Retrofit2Manager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @ClassName       AppApi
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/14 15:42
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
object AppApi {

    var mt : Any? = null
    private var isInit = false

    inline fun<reified T> Api(): T {
        if (mt == null || mt !is T)
            mt = Retrofit2Manager
                    .instance
                    .createApi(T::class.java!!)
        return mt as T
    }

    fun setIntercept(vararg intercept : Interceptor){
        val newBuilder = Retrofit2Manager.instance.okHttpClient!!.newBuilder()
        for (i in intercept.iterator()){
            newBuilder.addInterceptor(i)
        }
        newBuilder.build()
    }

    fun addHeader(map : Map<String,String>){
        Retrofit2Manager.instance.okHttpClient!!.newBuilder()
                .addInterceptor { chain ->
                    val oldRequest = chain.request()

                    val httpUrl = oldRequest.url()
                            .newBuilder()
                            .build()

                    val newRequest = oldRequest.newBuilder()
                    for (i in map.entries.iterator()){
                        newRequest.addHeader(i.key,i.value)
                    }
                    newRequest.url(httpUrl)
                    chain.proceed(newRequest.build())
                }
    }

    fun setTimeOut(read : Long = 10L,write : Long = 10L,connect : Long = 15L){
        val build = OkHttpClient.Builder()
                .readTimeout(read, TimeUnit.SECONDS)
                .writeTimeout(write, TimeUnit.SECONDS)
                .connectTimeout(connect, TimeUnit.SECONDS)
                .build()
        Retrofit2Manager.instance.okHttpClient = build
    }

    fun setConverterFactory(vararg factory : Converter.Factory){
        val retrofit = Retrofit.Builder()
                .baseUrl(Retrofit2Manager.instance.apiConfigProvider!!.apiBaseUrl)
                .client(Retrofit2Manager.instance.okHttpClient!!.newBuilder().build())
        for (i in factory.iterator()){
            retrofit.addConverterFactory(i)
        }
        retrofit.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        Retrofit2Manager.instance.retrofit = retrofit.build()
    }

    /* public static String getApiSignHeader() {
        if (Constants.getToken() != null) {
            return Constants.getToken().getSign_api();
        }
        return "123";
    }*/

    /**
     * 置空,使下次请求时重新获取配置
     */
    fun resetApi() {
        mt = null
    }
}