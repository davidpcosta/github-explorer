package me.davidcosta.github.api.response

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
