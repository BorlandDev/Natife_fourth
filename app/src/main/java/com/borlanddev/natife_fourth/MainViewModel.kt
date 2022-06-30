package com.borlanddev.natife_fourth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Int>>()
    val items: LiveData<List<Int>> = _items
    private val list = mutableListOf<Int>()

    init {
        genRandomNumber()
    }

    private fun genRandomNumber() {
        thread {
            while (true) {
                list.add(Random.nextInt(0, 100))
                Thread.sleep(2000)

                _items.postValue(list)
            }
        }
    }
}