package com.example.absensi.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.absensi.R
import com.example.absensi.databinding.ActivityLoginBinding
import com.example.absensi.dialog.MyDialog
import com.example.absensi.hawkstorage.HawkStorage
import com.example.absensi.model.LoginResponse
import com.example.absensi.networking.ApiServices
import com.example.absensi.networking.RetrofitClient
import com.example.absensi.views.main.MainActivity
import com.example.absensi.views.forgotpass.ForgotPasswordActivity
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            if (isFormValid(email, password)){
                loginToServer(email, password)
            }
        }

        binding.btnForgotPassword.setOnClickListener {
            startActivity<ForgotPasswordActivity>()
        }
    }

    private fun loginToServer(email: String, password: String) {
        val loginRequest = LoginRequest(email = email, password = password, deviceName = "mobile")
        val loginRequestString = Gson().toJson(loginRequest)
        MyDialog.showProgressDialog(this)

        ApiServices.getAbsensiServices()
                .loginRequest(loginRequestString)
                .enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                    ) {
                        MyDialog.hideDialog()
                        if (response.isSuccessful){
                            val user = response.body()?.user
                            val token = response.body()?.meta?.token
                            if (user != null && token != null){
                                HawkStorage.instance(this@LoginActivity).setUser(user)
                                HawkStorage.instance(this@LoginActivity).setToken(token)
                                goToMain()
                            }
                        }else{
                            val errorConverter: Converter<ResponseBody, LoginResponse> =
                                    RetrofitClient
                                            .getClient()
                                            .responseBodyConverter(
                                                    LoginResponse::class.java,
                                                    arrayOfNulls<Annotation>(0)
                                            )
                            var errorResponse: LoginResponse?
                            try {
                                response.errorBody()?.let {
                                    errorResponse = errorConverter.convert(it)
                                    MyDialog.dynamicDialog(
                                            this@LoginActivity,
                                            getString(R.string.failed),
                                            errorResponse?.message.toString()
                                    )
                                }
                            }catch (e: IOException){
                                e.printStackTrace()
                                Log.e(TAG, "Error: ${e.message}")
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        MyDialog.hideDialog()
                        Log.e(TAG, "Error: ${t.message}")
                    }

                })
    }

    private fun goToMain() {
        startActivity<MainActivity>()
        finishAffinity()
    }

    private fun isFormValid(email: String, password: String): Boolean {
        if (email.isEmpty()){
            binding.etEmailLogin.error = getString(R.string.please_fill_your_email)
            binding.etEmailLogin.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailLogin.error = getString(R.string.please_use_valid_email)
            binding.etEmailLogin.requestFocus()
        }else if (password.isEmpty()){
            binding.etEmailLogin.error = null
            binding.etPasswordLogin.error = getString(R.string.please_fill_your_password)
            binding.etPasswordLogin.requestFocus()
        }else{
            binding.etEmailLogin.error = null
            binding.etPasswordLogin.error = null
            return true
        }
        return false
    }

    companion object{
        private val TAG = LoginActivity::class.java.simpleName
    }
}