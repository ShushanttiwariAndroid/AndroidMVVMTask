package com.shushant.androidmvvmtask.network.service

import javax.inject.Inject

class DemoClient @Inject constructor(
        private val deezerService: DemoService
){

    suspend fun fetchData(accountId: String)
            = deezerService.fetchData(accountId)

}
