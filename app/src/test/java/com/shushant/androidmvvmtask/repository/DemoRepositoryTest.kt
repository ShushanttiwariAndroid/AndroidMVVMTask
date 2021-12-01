package com.shushant.androidmvvmtask.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.*
import com.shushant.MainCoroutineRule
import com.shushant.androidmvvmtask.network.Api.ACCOUNT_ID
import com.shushant.androidmvvmtask.network.service.DemoClient
import com.shushant.androidmvvmtask.network.service.DemoService
import com.shushant.androidmvvmtask.persistence.DemoDao
import com.shushant.utils.MockUtil.mockDummyList
import com.skydoves.sandwich.ApiResponse
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class DemoRepositoryTest {

    private lateinit var repository: DemoRepository
    private lateinit var client: DemoClient
    private val service: DemoService = mock()
    private val demoDao: DemoDao = mock()

    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        client = DemoClient(service)
        repository = DemoRepository(client, demoDao)
    }

    @Test
    fun fetchSandboxListFromNetworkTest() = runBlocking {
        val mockData = mockDummyList()
        val returnData = ApiResponse.of { Response.success(mockData) }
        whenever(demoDao.getData()).thenReturn(emptyList())
        whenever(service.fetchData()).thenReturn(returnData)

        repository.getData(onStart = {}, onSuccess = {}, onError = {}).test {
            val result = awaitItem()
            assertEquals(result, mockData)
            Assert.assertEquals(result[0].accountId, ACCOUNT_ID)
            awaitComplete()
        }

        verify(demoDao, atLeastOnce()).getData()
        verify(service, atLeastOnce()).fetchData()
        verify(demoDao, atLeastOnce()).insertData(mockData)
    }

    @Test
    fun deleteSandboxItemFromDatabase() = runBlocking {
        val mockData = mockDummyList()
        whenever(demoDao.insertData(mockData))
        whenever(demoDao.getData()).thenReturn(emptyList())
        repository.deleteItem(mockData[0]).test {
            val result = awaitItem()
            assertEquals(result.size,0)
            awaitComplete()
        }
        verify(demoDao, atLeastOnce()).delete(mockData[0].id)
        verifyNoMoreInteractions(demoDao)
    }
}