package com.example.absensi.views.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.absensi.R
import com.example.absensi.databinding.FragmentProfileBinding
import com.example.absensi.views.changepass.ChangePasswordActivity
import com.example.absensi.views.login.LoginActivity
import com.example.absensi.views.main.MainActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding?.btnChangePassword?.setOnClickListener {
            context?.startActivity<ChangePasswordActivity>()
        }

        binding?.btnChangeLanguage?.setOnClickListener {
            context?.toast("Change Language")
        }

        binding?.btnLogout?.setOnClickListener {
            context?.startActivity<LoginActivity>()
            (activity as MainActivity).finishAffinity()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}