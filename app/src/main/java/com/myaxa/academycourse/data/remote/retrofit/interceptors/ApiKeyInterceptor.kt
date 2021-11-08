package com.myaxa.academycourse.data.remote.retrofit.interceptors

import com.myaxa.academycourse.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(
                "api_key", BuildConfig.API_KEY
            )
            .build()

        val resultRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(resultRequest)
    }
}