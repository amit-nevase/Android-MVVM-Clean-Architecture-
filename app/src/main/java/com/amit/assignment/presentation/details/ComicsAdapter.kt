package com.amit.assignment.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amit.assignment.databinding.ItemListBinding
import com.amit.assignment.domain.model.Item
import com.amit.assignment.util.loadImage
import java.util.*
/**
 * list adapter of comics in character details page
 * */

class ComicsAdapter :
    PagingDataAdapter<Item, ComicsAdapter.ItemViewHolder>(ItemDiffCallback) {

    val items: MutableList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(list: List<Item>) {
        this.items.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.items.clear()
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val itemBinding: ItemListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Item) {
            itemBinding.imageViewIcon.loadImage(
                item.resourceURI,
                itemBinding.progressBar
            )
            itemBinding.textviewName.text = item.name
        }
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }
}
