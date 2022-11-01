package com.bendg.bg.presenter.ui.fragment.log_in

import androidx.lifecycle.ViewModel
import com.bendg.bg.utility.view_states.AuthenticationViewState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LogInViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginStatus = MutableStateFlow<AuthenticationViewState>(AuthenticationViewState())
    val loginStatus = _loginStatus.asStateFlow()

    fun loginResponse(email: String, password: String) {
        try {
            auth.let {
                it.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginStatus.value =
                                _loginStatus.value.copy(message = "You have log in successfully!")
                        } else {
                            _loginStatus.value =
                                _loginStatus.value.copy(message = "Email or Password is not correct!")
                        }
                    }
            }
        } catch (e: Throwable) {
            _loginStatus.value =
                _loginStatus.value.copy(message = e.message.toString())
        }
    }


}