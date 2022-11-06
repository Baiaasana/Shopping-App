package com.bendg.bg.presenter.ui.fragment.sign_up

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentSignUpBinding
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.utility.generateCardNumber
import com.bendg.bg.utility.snack
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun listeners() {

        binding.tvLogIn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
        }

        binding.tvConditions.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.termsfeed.com/public/uploads/2021/12/sample-terms-conditions-agreement.pdf")
            startActivity(openURL)
        }

        binding.btnSignUp.setOnClickListener {
            when {
                isEmptyField() -> it.snack(getString(R.string.empty_fields_error))
                isNotValidNames() -> it.snack(getString(R.string.first_char_number_error))
                isNotValidUsername() -> it.snack(getString(R.string.first_char_number_error))
                !isValidEmail() -> it.snack(getString(R.string.invalid_email_error))
                isNotValidPassword() -> it.snack(getString(R.string.password_length_limit))
                !binding.checkBox.isChecked -> it.snack(getString(R.string.check_box))
                else -> {
                    register()
                    registerObserver()
                    navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin(){
        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
    }

    private fun registerObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerStatus.collect {
                    if (it.message!!.isNotEmpty()) {
                        Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun register() {
        val firstName = binding.etFirstname.text.toString()
        val lastName = binding.etLastname.text.toString()
        val userName = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val card = UserModel.Card(cardNumber = generateCardNumber())
        val userInfo = UserModel(firstName, lastName, userName, email, password, cards = card)
        viewModel.signUpResponse(email = email, password = password, userInfo = userInfo)
    }

    override fun init() {
    }

    private fun isNotValidPassword(): Boolean = with(binding) {
        return@with binding.etPassword.text.toString().length < 8
    }

    private fun isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding) {
        return@with etEmail.text.toString().isEmpty() ||
                etPassword.text.toString().isEmpty() ||
                etUsername.text.toString().isEmpty() ||
                etFirstname.text.toString().isEmpty() ||
                etLastname.text.toString().isEmpty()
    }

    private fun isNotValidUsername(): Boolean = with(binding) {
        return@with etUsername.text.toString()[0].isDigit()
    }

    override fun observers() {
    }

    private fun isNotValidNames(): Boolean = with(binding) {
        return@with etFirstname.text.toString()[0].isDigit() ||
                etLastname.text.toString()[0].isDigit()
    }
}