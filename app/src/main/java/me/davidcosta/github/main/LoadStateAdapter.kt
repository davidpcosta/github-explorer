package me.davidcosta.github.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.davidcosta.github.R

class LoadStateAdapter:
    ListAdapter<Boolean, LoadStateViewHolder>(object :
        DiffUtil.ItemCallback<Boolean>() {
        override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Boolean, newItem: Boolean) =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadStateViewHolder =
        LoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.load_state_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LoadStateViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}