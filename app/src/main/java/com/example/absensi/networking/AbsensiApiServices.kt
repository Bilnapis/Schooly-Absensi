package com.example.absensi.networking

import com.example.absensi.model.AttendanceResponse
import com.example.absensi.model.HistoryResponse
import com.example.absensi.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AbsensiApiServices {

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("api/auth/login")
    fun loginRequest(@Body body: String): Call<LoginResponse>

    @Multipart
    @Headers("Accept: application/json")
    @POST("api/attendance")
    fun attend(@Header("Authorization") token: String,
               @PartMap params: HashMap<String, RequestBody>,
               @Part photo: MultipartBody.Part
    ): Call<AttendanceResponse>


    @Headers("Accept: application/json")
    @GET("api/attendance/history")
    fun getHistoryAttendance(@Header("Authorization") token: String,
                             @Query("from") fromDate: String,
                             @Query("to") toDate: String
    ): Call<HistoryResponse>
}