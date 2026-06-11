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

data class LoginRequest(
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = ""
)

data class UserRegisterRequest(
    @SerializedName("name") var name: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = ""
)
