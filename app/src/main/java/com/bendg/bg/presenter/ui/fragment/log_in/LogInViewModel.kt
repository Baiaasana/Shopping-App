package com.bendg.bg.presenter.ui.fragment.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.extensions.view_states.AuthenticationViewState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginStatus = MutableSharedFlow<AuthenticationViewState>()
    val loginStatus = _loginStatus.asSharedFlow()

    fun loginResponse(email: String, password: String) {
        try {
            auth.let {
                it.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        viewModelScope.launch {
                            if (task.isSuccessful) {
                                _loginStatus.emit(AuthenticationViewState(message = "1"))
                            } else {
                                _loginStatus.emit(AuthenticationViewState(message = "Email or Password is not correct!"))
                            }
                        }
                    }
            }
        } catch (e: Throwable) {
            viewModelScope.launch {
                _loginStatus.emit(AuthenticationViewState(message = e.message.toString()))
            }
        }
    }
}