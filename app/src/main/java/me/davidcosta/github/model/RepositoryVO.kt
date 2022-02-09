package me.davidcosta.github.model

data class RepositoryVO(
    val id: Int,
    val name: String,
    val fullName: String,
    val stargazersCount: String,
    val forksCount: String,
    val language: String,
    val ownerVO: OwnerVO
)
