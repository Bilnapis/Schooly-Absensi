package com.example.absensi.networking

object ApiServices {
    fun getAbsensiServices(): AbsensiApiServices{
        return RetrofitClient
                .getClient()
                .create(AbsensiApiServices::class.java)
    }
}