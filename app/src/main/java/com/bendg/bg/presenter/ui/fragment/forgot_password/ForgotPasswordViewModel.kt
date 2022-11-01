package com.bendg.bg.presenter.ui.fragment.forgot_password

import androidx.lifecycle.ViewModel
import com.bendg.bg.utility.view_states.AuthenticationViewState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgotPasswordViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _recoveryStatus =
        MutableStateFlow<AuthenticationViewState>(AuthenticationViewState())
    val recoveryStatus = _recoveryStatus.asStateFlow()

    fun resetPasswordResponse(email: String) {
        try {
            auth.let { authentication ->
                authentication.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _recoveryStatus.value =
                                _recoveryStatus.value.copy(message = "A recovery message has been sent to your email, please check it")
                        } else {
                            _recoveryStatus.value =
                                _recoveryStatus.value.copy(message = "Failed to send recovery message, please try later!")
                        }
                    }
            }
        } catch (e: Throwable) {
            _recoveryStatus.value =
                _recoveryStatus.value.copy(message = e.message.toString())
        }
    }
}