package com.micropole.baseapplibrary.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Description:
 * Created by DarkHorse on 2018/6/11.
 */
class RetryInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        var response = doRequest(chain, request)
        var tryCount = 0
        while ((response == null || !response.isSuccessful) && tryCount < 3) {
            tryCount++
            response = doRequest(chain, request)
        }
        if (response == null) {
            throw Exception(RuntimeException("网络错误"))
        }
        return response
    }

    private fun doRequest(chain: Interceptor.Chain, request: Request): Response? {
        var response: Response? = null
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
        }
        return response
    }
}
