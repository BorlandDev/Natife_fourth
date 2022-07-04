package com.borlanddev.natife_fourth.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.borlanddev.natife_fourth.databinding.ItemListBinding

class ItemAdapter: RecyclerView.Adapter<ItemHolder>() {

    private var items: List<Int> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun addNumbers(_items: List<Int>) {
        items = _items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}

class ItemHolder(
    private val binding: ItemListBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Int) {
        binding.numberCell.text = item.toString()
    }
}