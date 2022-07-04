package com.borlanddev.natife_fourth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.borlanddev.natife_fourth.adapter.ItemAdapter
import com.borlanddev.natife_fourth.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var itemAdapter: ItemAdapter? = null
    private val mainVM: MainViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val rx = Rx()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        itemAdapter = ItemAdapter()

        //observeWithLiveData()
        observeWithRx(rx)
        //observeWithCoroutines(this)

        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 3)
        binding?.recyclerView?.adapter = itemAdapter
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.dispose()
    }

    private fun observeWithLiveData() {
        mainVM.genRandomNumber()
        mainVM.numbers.observe(this) {
            itemAdapter?.addNumbers(it)
        }
    }

    private fun observeWithRx(rx: Rx) {
        compositeDisposable.add(rx.source
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                itemAdapter?.addNumbers(it)
            }, {
                it.printStackTrace()
            }))
          }

    private fun observeWithCoroutines(viewLifeCycleOwner: AppCompatActivity) {
        val list = mutableListOf<Int>()

        viewLifeCycleOwner.lifecycleScope.launch(Dispatchers.Main) {

            flow {
                while (true) {
                    emit(Random.nextInt(0, 100))
                    delay(2000)
                }
            }.collect {
                list.add(it)
                itemAdapter?.addNumbers(list)
            }
        }
    }
}
