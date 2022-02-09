package me.davidcosta.github.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.davidcosta.github.R
import me.davidcosta.github.model.RepositoryVO

class RepositoryAdapter :
    ListAdapter<RepositoryVO, RepositoryViewHolder>(object :
        DiffUtil.ItemCallback<RepositoryVO>() {
        override fun areItemsTheSame(oldItem: RepositoryVO, newItem: RepositoryVO) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RepositoryVO, newItem: RepositoryVO) =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.repository_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}