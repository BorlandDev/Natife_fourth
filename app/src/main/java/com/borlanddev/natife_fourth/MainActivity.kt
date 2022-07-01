package com.borlanddev.natife_fourth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.borlanddev.natife_fourth.adapter.ItemAdapter
import com.borlanddev.natife_fourth.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var itemAdapter: ItemAdapter? = null
    private val mainVM: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val rx = Rx()

        itemAdapter = ItemAdapter()

        //observeWithLiveData()
        observeWithRx(rx)

        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 3)
        binding?.recyclerView?.adapter = itemAdapter
    }

    private fun observeWithLiveData() {
        mainVM.items.observe(this) {
            itemAdapter?.addNumber(it)
        }
    }

    private fun observeWithRx(rx: Rx) {
        rx.source
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                itemAdapter?.addNumber(it)
            }
    }

}
