package com.borlanddev.natife_fourth

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Rx {

    private val list = mutableListOf<Int>()

    val source: Observable<MutableList<Int>> = Observable
        .interval( 0, 2, TimeUnit.SECONDS)
        .flatMap {
            return@flatMap Observable.create { emitter ->

                list.add(Random.nextInt(0, 100))
                emitter.onNext(list)
            }
        }



}