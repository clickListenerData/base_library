package com.micropole.newproject

import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.micropole.baseapplibrary.appconst.Constants
import com.xx.baseutilslibrary.entity.BaseResponseEntity
import com.xx.baseutilslibrary.entity.BaseResponseStatusEntity
import com.xx.baseutilslibrary.network.gson.ZipHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

/**
 * @ClassName       DoubleTokenIntercept
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/16 9:44
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class DoubleTokenIntercept : Interceptor {

    private val UTF8 = Charset.forName("UTF-8")
    private val REFRESH_URL = "apitoken_refresh"
    private val LONG_TOKEN_HEADER = "long_token"
    val SHORT_TOKEN_HEADER = "short_token"

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val response = chain?.proceed(request)
        val body = response?.body()
        val source = body?.source()
        source?.request(Long.MAX_VALUE)
        assert(source != null)
        val buffer = source!!.buffer()
        val get = response.headers().get("Content-Encoding")
        val clone = buffer.clone()
        var str:String
        if (get.equals("gzip",true)){
            str = ZipHelper.decompressForGzip(clone.readByteArray())
        }else{
            val contentType = body.contentType()
            val charset = contentType?.charset(UTF8) ?: UTF8
            str = clone.readString(charset)
        }
        try {
            val entity = Gson().fromJson<BaseResponseStatusEntity>(str, BaseResponseStatusEntity::class.java)
            if (entity != null){
                if (entity.code == Constants.LONG_TOKEN_INVALID){

                }else if (entity.code == Constants.SHORT_TOKEN_INVALID){
                    if (!request!!.url().url().toString().contains(REFRESH_URL)){
                        val build = Api.api().refresh_token()
                                .request()
                                .newBuilder()
                                .addHeader(LONG_TOKEN_HEADER, Constants.getLongToken())
                                .build()
                        val toString = chain.proceed(build).body()?.string()
                        val fromJson = Gson().fromJson<BaseResponseEntity<String>>(toString, object : TypeToken<BaseResponseEntity<String>>() {
                        }.type)
                        synchronized(Utils.getApp()){
                            val token = fromJson.data
                            if (token != null){
                                Constants.putShortToken(token)
                            }
                            val build1 = request.newBuilder().header(SHORT_TOKEN_HEADER, token!!).build()
                            response.body()?.close()
                            return chain.proceed(build1)
                        }
                    }
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
        return response
    }
}