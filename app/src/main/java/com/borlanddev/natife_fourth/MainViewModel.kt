package com.borlanddev.natife_fourth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.concurrent.thread
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val _numbers = MutableLiveData<List<Int>>()
    val numbers: LiveData<List<Int>> = _numbers
    private val list = mutableListOf<Int>()
    private var myThread: Thread? = null

    fun genRandomNumber() {
        myThread = thread {

            while (myThread?.isInterrupted != true) {
                list.add(Random.nextInt(0, 100))
                Thread.sleep(2000)
                _numbers.postValue(list)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        myThread?.interrupt()
    }
}