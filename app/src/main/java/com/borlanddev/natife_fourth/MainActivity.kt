package com.borlanddev.natife_fourth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.borlanddev.natife_fourth.adapter.ItemAdapter
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.borlanddev.natife_fourth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var itemAdapter: ItemAdapter? = null
    private val mainVM: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        itemAdapter = ItemAdapter()

        observeWithLiveData()

        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 3)
        binding?.recyclerView?.adapter = itemAdapter
    }

    private fun observeWithLiveData() {
        mainVM.items.observe(this) {
            itemAdapter?.addNumber(it)
        }
    }
}
