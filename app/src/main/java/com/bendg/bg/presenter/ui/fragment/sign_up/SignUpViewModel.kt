package com.bendg.bg.presenter.ui.fragment.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.extensions.view_states.AuthenticationViewState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    private val _registerStatus =
        MutableSharedFlow<AuthenticationViewState>()
    val registerStatus = _registerStatus.asSharedFlow()

    fun signUpResponse(email: String, password: String, userInfo: UserModel) {
        try {
            auth.let { authentication ->
                authentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        saveInfoInDatabase(task, userInfo)
                    }
            }
        } catch (e: Throwable) {
            viewModelScope.launch {
                _registerStatus.emit(AuthenticationViewState(message = e.message.toString()))
            }
        }
    }

    private fun saveInfoInDatabase(task: Task<AuthResult>, userInfo: UserModel) {
        viewModelScope.launch {
            if (task.isSuccessful) {
                databaseReference.child(auth.currentUser?.uid.toString())
                    .setValue(userInfo)
                _registerStatus.emit(AuthenticationViewState(message = "You have registered successfully!"))
            } else {
                _registerStatus.emit(AuthenticationViewState(message = "Registration failed. Please try later!"))
            }
        }
    }
}