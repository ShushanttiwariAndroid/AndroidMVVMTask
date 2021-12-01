package com.shushant.androidmvvmtask.network.service

import com.shushant.androidmvvmtask.models.SandboxResponseClassItem
import com.shushant.androidmvvmtask.network.Api.ACCOUNT_ID
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DemoService {
    @GET("driver")
    suspend fun fetchData(
        @Query("accountId") accountId: String = ACCOUNT_ID
    ): ApiResponse<List<SandboxResponseClassItem>>
}
