package com.bendg.bg.presenter.ui.fragment.sign_up

import androidx.lifecycle.ViewModel
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.utility.viewStates.AuthenticationViewState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    private val _registerStatus = MutableStateFlow<AuthenticationViewState>(AuthenticationViewState())
    val registerStatus = _registerStatus.asStateFlow()

    fun signUpResponse(email: String, password: String, userInfo: UserModel) {
        try {
            auth.let { authentication ->
                authentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            databaseReference.child(auth.currentUser?.uid.toString())
                                .setValue(userInfo)
                            _registerStatus.value =
                                _registerStatus.value.copy(message = "You have registered successfully!")
                        } else {
                            _registerStatus.value =
                                _registerStatus.value.copy(message = "Registration failed. Please try later!")
                        }
                    }
            }
        } catch (e: Throwable) {
            _registerStatus.value =
                _registerStatus.value.copy(message = e.message.toString())
        }
    }
}