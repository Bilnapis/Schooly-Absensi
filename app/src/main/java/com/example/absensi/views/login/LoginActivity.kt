package com.example.absensi.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.absensi.R
import com.example.absensi.databinding.ActivityLoginBinding
import com.example.absensi.views.main.MainActivity
import com.example.absensi.views.forgotpass.ForgotPasswordActivity
import org.jetbrains.anko.startActivity

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
            startActivity<MainActivity>()
        }

        binding.btnForgotPassword.setOnClickListener {
            startActivity<ForgotPasswordActivity>()
        }
    }
}