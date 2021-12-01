package com.shushant.androidmvvmtask.ui

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shushant.MainCoroutineRule
import com.shushant.androidmvvmtask.network.Api.ACCOUNT_ID
import com.shushant.androidmvvmtask.network.service.DemoClient
import com.shushant.androidmvvmtask.network.service.DemoService
import com.shushant.androidmvvmtask.persistence.DemoDao
import com.shushant.androidmvvmtask.repository.DemoRepository
import com.shushant.utils.MockUtil.mockDummyList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class MainViewModelTest{
    private lateinit var viewModel: MainViewModel
    private lateinit var mainRepository: DemoRepository
    private val demoService: DemoService = mock()
    private val demoClient: DemoClient = DemoClient(demoService)
    private val demoDao: DemoDao = mock()

    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    @Before
    fun setup() {
        mainRepository = DemoRepository(demoClient, demoDao)
        viewModel = MainViewModel(mainRepository)
    }

    @Test
    fun fetchPokemonListTest() = runBlocking {
        val mockData = mockDummyList()
        whenever(demoDao.getData()).thenReturn(mockData)

        val fetchedDataFlow = mainRepository.getData(
            onStart = {},
            onSuccess = {},
            onError = {}
        ).test(2.toDuration(DurationUnit.SECONDS)) {
            val item = awaitItem()
            Assert.assertEquals(item[0].accountId, ACCOUNT_ID)
            awaitComplete()
        }
        verify(demoDao, atLeastOnce()).getData()

        fetchedDataFlow.apply {
            // runBlocking should return Unit
        }
    }
}