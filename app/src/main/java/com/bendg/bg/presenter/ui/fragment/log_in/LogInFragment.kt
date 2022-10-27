package com.bendg.bg.presenter.ui.fragment.log_in

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentLogInBinding
import com.bendg.bg.presenter.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    override fun listeners() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgotPasswordFragment)
        }
    }
    override fun init() {
    }

    override fun observers() {
    }
}