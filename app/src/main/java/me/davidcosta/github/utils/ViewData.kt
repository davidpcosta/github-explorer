package me.davidcosta.github.utils

data class ViewData<T>(
    val status: LoadState,
    val data: T,
    val error: Throwable? = null
)