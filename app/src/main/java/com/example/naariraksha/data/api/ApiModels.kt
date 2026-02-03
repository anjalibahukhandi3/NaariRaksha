package com.example.naariraksha.data.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("userId") val userId: Long?,
    @SerializedName("name") val name: String?
)

data class RegisterResponse(
    @SerializedName("message") val message: String
)
