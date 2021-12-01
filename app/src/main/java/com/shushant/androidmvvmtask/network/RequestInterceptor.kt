package com.shushant.androidmvvmtask.network

import com.shushant.androidmvvmtask.network.Api.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader(
                "Authorization",TOKEN
            ).addHeader("User-Agent","application/json")
            .build()

        val requestBuilder = newRequest.newBuilder()
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
