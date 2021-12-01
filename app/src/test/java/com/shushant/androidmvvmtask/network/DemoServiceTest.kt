package com.shushant.androidmvvmtask.network

import com.shushant.MainCoroutineRule
import com.shushant.androidmvvmtask.network.Api.ACCOUNT_ID
import com.shushant.androidmvvmtask.network.service.DemoService
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


class DemoServiceTest : ApiAbstract<DemoService>() {

    private lateinit var service: DemoService

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // firstly created to service. Then we'll write to test function for each of api call .
    @Before
    fun initService() {
        print("init")
        service = createService(DemoService::class.java)
    }


    @Throws(IOException::class)
    @Test
    fun fetchGenreListTest(): Unit = runBlocking {
        enqueueResponse("/response.json")
        val response = service.fetchData()
        mockWebServer.takeRequest()
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        responseBody.let { safeList ->
            assertThat(safeList.size, `is`(82))
            assertThat(safeList[0].accountId, `is`(ACCOUNT_ID))
        }

    }

}


