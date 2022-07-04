package com.borlanddev.natife_fourth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.borlanddev.natife_fourth.adapter.ItemAdapter
import com.borlanddev.natife_fourth.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var itemAdapter: ItemAdapter? = null
    private val mainVM: MainViewModel by viewModels()
    private val rx = Rx()
    private val coroutine = Coroutine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        itemAdapter = ItemAdapter()

        //observeWithLiveData()
        //observeWithRx(rx)
        //observeWithCoroutines()

        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 3)
        binding?.recyclerView?.adapter = itemAdapter
    }

    private fun observeWithLiveData() {
        mainVM.items.observe(this) {
            itemAdapter?.addNumbers(it)
        }
    }

    private fun observeWithRx(rx: Rx) {
        rx.source
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                itemAdapter?.addNumbers(it)
            }
    }

    private fun observeWithCoroutines() {
        val list = mutableListOf<Int>()

        GlobalScope.launch(Dispatchers.Main) {
            coroutine.numbers.collect {
                list.add(it)
                itemAdapter?.addNumbers(list)
            }
        }
    }
}
