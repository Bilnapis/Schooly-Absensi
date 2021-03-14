package com.example.absensi.networking

import com.example.absensi.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AbsensiApiServices {

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("siswa/api/auth/login")

    fun loginRequest(@Body body: String): Call<LoginResponse>
}