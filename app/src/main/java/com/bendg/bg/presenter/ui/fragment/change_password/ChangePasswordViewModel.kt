package com.bendg.bg.presenter.ui.fragment.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.extensions.view_states.AuthenticationViewState
import com.google.android.gms.tasks.Task
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
                    updatePassword(newPassword = newPassword)
                } else {
                    viewModelScope.launch {
                        _updateState.emit(AuthenticationViewState(message = "Old Password is not correct!"))
                    }
                }
            }
        }
    }

    private fun updatePassword(newPassword: String) {
        auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
            setNewPassword(task = task, newPassword = newPassword)
        }
    }

    private fun setNewPassword(task: Task<Void>, newPassword: String) {
        viewModelScope.launch {
            if (task.isSuccessful) {
                updatePasswordInDatabase(newPassword = newPassword)
                _updateState.emit(AuthenticationViewState(message = "Password updated successfully!"))
            } else {
                _updateState.emit(AuthenticationViewState(message = "Password did not changed, try later!"))
            }
        }
    }

    private fun updatePasswordInDatabase(newPassword: String) {
        databaseReference.child(auth.currentUser?.uid!!)
            .child("password")
            .setValue(newPassword)
    }
}
