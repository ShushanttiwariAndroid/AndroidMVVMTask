package com.shushant.androidmvvmtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shushant.MainCoroutineRule
import com.shushant.androidmvvmtask.models.SandboxResponseClassItem
import com.shushant.androidmvvmtask.network.Api.ACCOUNT_ID
import com.shushant.androidmvvmtask.network.service.DemoClient
import com.shushant.androidmvvmtask.network.service.DemoService
import com.shushant.androidmvvmtask.persistence.DemoDao
import com.shushant.androidmvvmtask.repository.DemoRepository
import com.shushant.utils.MockUtil.mockDummyList
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class MainViewModelTest {
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

        val observer: Observer<List<SandboxResponseClassItem>?> = mock()
        val fetchedData: LiveData<List<SandboxResponseClassItem>> =
            mainRepository.getData(onSuccess = {}, onError = {}, onStart = {}).asLiveData()
        fetchedData.observeForever(observer)
        delay(500L)
        verify(demoDao, atLeastOnce()).getData()
        verify(observer).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }
}