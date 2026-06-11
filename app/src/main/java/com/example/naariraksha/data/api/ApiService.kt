package com.example.naariraksha.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/register")
    suspend fun register(@Body request: UserRegisterRequest): Response<RegisterResponse>

    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/users/sos")
    suspend fun sendSOS(@Body payload: Map<String, String>): Response<Map<String, String>>
}
