package com.borlanddev.natife_fourth

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random

class Coroutine {

    val numbers = MutableStateFlow(0)

    private val genRandomNumber = GlobalScope.launch {

        withContext(Dispatchers.Default) {
            while (true) {
                delay(2000)
                numbers.value = Random.nextInt(0, 100)
            }
        }
    }
}