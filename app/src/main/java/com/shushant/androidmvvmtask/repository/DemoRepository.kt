package com.shushant.androidmvvmtask.repository

import androidx.annotation.WorkerThread
import com.shushant.androidmvvmtask.mapper.ErrorResponseMapper
import com.shushant.androidmvvmtask.models.SandboxResponseClassItem
import com.shushant.androidmvvmtask.network.Api.ACCOUNT_ID
import com.shushant.androidmvvmtask.network.service.DemoClient
import com.shushant.androidmvvmtask.persistence.DemoDao
import com.skydoves.sandwich.*
import com.skydoves.whatif.whatIfNotNullOrEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class DemoRepository @Inject constructor(
    private val demoClient: DemoClient,
    private val demoDao: DemoDao
) : Repository {

    init {
        Timber.d("Injection DemoRepository")
    }

    @WorkerThread
    fun getData(
        accountId: String=ACCOUNT_ID,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        onStart: () -> Unit
    ) = flow {

        var sandboxData = demoDao.getData()

        if (sandboxData.isEmpty()) {
            val response = demoClient.fetchData(accountId)
            response.suspendOnSuccess {
                sandboxData = data
                sandboxData.whatIfNotNullOrEmpty {
                    demoDao.insertData(sandboxData)
                    emit(demoDao.getData())
                }
            }
                .onError {
                    map(ErrorResponseMapper) {
                        onError("[Code: $code]: $message")
                    }
                    Timber.e(errorBody.toString())
                    onError(errorBody.toString())
                }
                .onException {
                    Timber.e(message)
                    onError(message ?: "Unknown exception occurred")
                }
        } else {
            emit(sandboxData)
        }
    }.onStart { onStart() }.onCompletion { onSuccess() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun deleteItem(item: SandboxResponseClassItem) = flow {
        demoDao.delete(item.id)
        emit(demoDao.getData())
    }.flowOn(Dispatchers.IO)
}
