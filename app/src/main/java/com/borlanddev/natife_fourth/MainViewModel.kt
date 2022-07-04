package com.borlanddev.natife_fourth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val _numbersFromLiveData = MutableLiveData<List<Int>>()
    val numbersFromLiveData: LiveData<List<Int>> = _numbersFromLiveData
    private val list = mutableListOf<Int>()
    private var myThread: Thread? = null
    val numbersFromFlow = MutableStateFlow(0)


    fun genRandomNumberWithThread() {
        myThread = thread {
            while (true) {
                list.add(Random.nextInt(0, 100))
                Thread.sleep(2000)
                _numbersFromLiveData.postValue(list)
            }
        }
    }


    fun genRandomNumberWithFlow() {
        viewModelScope.launch {

            withContext(Dispatchers.Default) {
                while (true) {
                    delay(2000)
                    numbersFromFlow.value = Random.nextInt(0, 100)
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        myThread?.stop()
    }
}