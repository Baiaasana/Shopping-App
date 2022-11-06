package com.bendg.bg.presenter.ui.fragment.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.utility.view_states.AuthenticationViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    private val _updateState = MutableSharedFlow<AuthenticationViewState>()
    val updateState = _updateState.asSharedFlow()

    fun updatePassword(oldPass: String, newPassword: String) {

        databaseReference.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val password = it.child("password").value
                if (password.toString() == oldPass) {
                    auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                        viewModelScope.launch {
                            if (task.isSuccessful) {
                                databaseReference.child(auth.currentUser?.uid!!).child("password")
                                    .setValue(newPassword)
                                _updateState.emit(AuthenticationViewState(message = "success"))
                            } else {
                                viewModelScope.launch {
                                    _updateState.emit(AuthenticationViewState(message = "Password did not changed, try later!"))
                                }
                            }
                        }
                    }
                } else {
                    viewModelScope.launch {
                        _updateState.emit(AuthenticationViewState(message = "old Password did not match"))
                    }
                }
            }
        }
    }
}
