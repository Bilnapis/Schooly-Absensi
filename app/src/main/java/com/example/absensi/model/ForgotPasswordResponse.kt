package com.example.absensi.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(

	@field:SerializedName("message")
	val message: String? = null
)
