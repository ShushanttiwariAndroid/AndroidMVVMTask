package com.shushant.androidmvvmtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shushant.androidmvvmtask.models.SandboxResponseClassItem
import com.shushant.androidmvvmtask.network.Api.ACCOUNT_ID
import com.shushant.androidmvvmtask.repository.DemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val demoRepository: DemoRepository
) : ViewModel() {

    fun deleteItemFromDb(item: SandboxResponseClassItem) {
        viewModelScope.launch {
            demoRepository.deleteItem(item).collectLatest {
                _data.postValue(it.toMutableList())
            }
        }
    }

    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    private val loadingState: LiveData<Boolean> = _loadingState

    val _data = MutableLiveData<MutableList<SandboxResponseClassItem>>()
    val data: LiveData<MutableList<SandboxResponseClassItem>> = _data



    private val pokemonListFlow = demoRepository.getData(
        accountId = ACCOUNT_ID,
        onStart = { _loadingState.postValue(true) },
        onSuccess = { _loadingState.postValue(false) },
        onError = { Timber.e(it) }
    )

    init {
        viewModelScope.launch {
            pokemonListFlow.collectLatest {
                _data.postValue(it.toMutableList())
            }
        }
    }

}