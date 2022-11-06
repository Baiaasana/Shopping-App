package com.bendg.bg.presenter.ui.fragment.forgot_password

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentForgotPasswordBinding
import com.bendg.bg.utility.snack
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun listeners() {
        binding.btnSend.setOnClickListener {
            if (binding.etEmail.text.toString().isEmpty()) {
                it.snack(getString(R.string.empty_fields_error))
            } else {
                resetPassword()
                observerPasswordRecovery()
            }
        }
    }
    override fun init() {
    }

    private fun resetPassword() {
        viewModel.resetPasswordResponse(binding.etEmail.text.toString())
    }

    private fun observerPasswordRecovery() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recoveryStatus.collect {
                    if (it.message!!.isNotEmpty()) {
                        Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
    override fun observers() {
    }
}