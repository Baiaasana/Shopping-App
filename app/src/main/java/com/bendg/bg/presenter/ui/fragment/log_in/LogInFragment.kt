package com.bendg.bg.presenter.ui.fragment.log_in

import android.content.Intent
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentLogInBinding
import com.bendg.bg.presenter.ui.activity.MainActivity
import com.bendg.bg.utility.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun listeners() {
        binding.btnLogin.setOnClickListener { snack ->

            when {
                isEmptyField() -> snack.snack(getString(R.string.empty_fields_error))
                !isValidEmail() -> snack.snack(getString(R.string.invalid_email_error))
                else -> {
                    login()
                    loginObserver()
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.loginStatus.collectLatest {
                            snack.snack(it.message.toString())
                        }
                    }
                }
            }
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgotPasswordFragment)
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.loginResponse(email = email, password = password)
    }

    private fun loginObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginStatus.collect {
                    if (it.message.toString() == "1") {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                    }
                }
            }
        }
    }

    override fun init() {
    }

    override fun observers() {
    }

    private fun isValidEmail(): Boolean =
        EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding.etEmail.text.toString().isEmpty() ||
                binding.etPassword.text.toString().isEmpty()
    }
}