package me.davidcosta.github.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.github.databinding.LoadStateItemBinding

class LoadStateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val binding = LoadStateItemBinding.bind(itemView)

    fun bind(isLoading: Boolean) {
        binding.loadStateItemProgress.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}