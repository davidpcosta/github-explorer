package me.davidcosta.github.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.davidcosta.github.databinding.RepositoryItemBinding
import me.davidcosta.github.model.RepositoryVO

class RepositoryViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = RepositoryItemBinding.bind(itemView)

    fun bind(repositoryVO: RepositoryVO) {
        binding.repositoryItemName.text = repositoryVO.name
        binding.repositoryItemAuthorName.text = repositoryVO.ownerVO.login
        binding.repositoryItemStarCount.text = repositoryVO.stargazersCount
        binding.repositoryItemForksCount.text = repositoryVO.forksCount
        Picasso.get().load(repositoryVO.ownerVO.avatarUrl).into(binding.repositoryItemAvatar)

        if (repositoryVO.language.isNotBlank()) {
            binding.repositoryItemLanguage.text = repositoryVO.language
            binding.repositoryItemLanguage.visibility = View.VISIBLE
        } else {
            binding.repositoryItemLanguage.visibility = View.GONE
        }
    }

}