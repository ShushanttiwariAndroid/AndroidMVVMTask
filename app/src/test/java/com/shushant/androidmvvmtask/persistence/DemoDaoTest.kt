package com.shushant.androidmvvmtask.persistence

import android.os.Build
import com.shushant.utils.MockUtil.mockDummyList
import com.shushant.utils.MockUtil.mockSandBoxItems
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1], manifest = Config.NONE)
class DemoDaoTest: LocalDatabase(){

    private lateinit var deezerDao: DemoDao

    @Before
    fun init(){
        deezerDao = db.demoDao()
    }

    @Test
    fun insertAndLoadSandboxList() = runBlocking {
        val mockDataList  = mockDummyList()
        deezerDao.insertData(mockDataList)

        // checking insert process
        val loadFromDB = deezerDao.getData()

        // checking first data.
        val mockData = mockSandBoxItems()
        assertThat(loadFromDB[0].toString(),`is`(mockData.toString()))

    }

    @Test
    fun deleteItemFromSandboxDb() = runBlocking {
        val mockDataList  = mockDummyList()
        deezerDao.insertData(mockDataList)
        deezerDao.delete(mockSandBoxItems().id)
        assertThat(deezerDao.getData().size.toString(), `is` (0.toString()))

    }
}