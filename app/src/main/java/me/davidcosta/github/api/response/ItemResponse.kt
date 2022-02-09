package me.davidcosta.github.api.response

import com.google.gson.annotations.SerializedName

data class ItemResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("language") val language: String?,
    @SerializedName("owner") val owner: OwnerResponse
)
