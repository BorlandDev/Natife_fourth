package com.borlanddev.natife_fourth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.borlanddev.natife_fourth.adapter.ItemAdapter
import com.borlanddev.natife_fourth.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        //observeWithRx(rx)
        observeWithCoroutines()

        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 3)
        binding?.recyclerView?.adapter = itemAdapter
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()

    }


    private fun observeWithLiveData() {
        mainVM.genRandomNumberWithThread()
        mainVM.numbersFromLiveData.observe(this) {
            itemAdapter?.addNumbers(it)
        }
    }

    private fun observeWithRx(rx: Rx) {
        rx.source
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itemAdapter?.addNumbers(it)
            }, {
                it.printStackTrace()
            })
    }

    private fun observeWithCoroutines() {
        mainVM.genRandomNumberWithFlow()
        val list = mutableListOf<Int>()

        this.lifecycleScope.launch(Dispatchers.Main) {
            this@MainActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mainVM.numbersFromFlow.collect {
                    list.add(it)
                    itemAdapter?.addNumbers(list)
                }
            }
        }
    }
}
