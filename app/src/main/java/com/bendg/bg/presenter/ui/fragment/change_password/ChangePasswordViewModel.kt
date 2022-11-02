package com.bendg.bg.presenter.ui.fragment.change_password

import androidx.lifecycle.ViewModel
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.utility.view_states.AuthenticationViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChangePasswordViewModel : ViewModel() {

//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val databaseReference: DatabaseReference =
//        FirebaseDatabase.getInstance().getReference("userInfo")
//
//    private val _updateState = MutableStateFlow<AuthenticationViewState>(AuthenticationViewState())
//    val updateState = _updateState.asStateFlow()
//
//    fun updatePassword(newPassword: String){
//        auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                databaseReference.child(auth.currentUser?.uid!!).child("password").setValue(newPassword)
//                _updateState.value = _updateState.value.copy(message = "successsssss")
//            }else{
//                _updateState.value = _updateState.value.copy(message = "errrrorrr")
//            }
//        }
//
//    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    private val _updateState = MutableStateFlow<AuthenticationViewState>(AuthenticationViewState())
    val updateState = _updateState.asStateFlow()

    fun updatePassword(oldPass:String, newPassword: String){

        databaseReference.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInformation = snapshot.getValue(UserModel::class.java) ?: return
                    if(userInformation.password.toString() == oldPass){
                        auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                // an shemowmebis gareshe ganaaxle an databaseshi ar ganaaxlo
                                databaseReference.child(auth.currentUser?.uid!!).child("password").setValue(newPassword).addOnCompleteListener {
                                    _updateState.value = _updateState.value.copy(message = "Your password changed successfully!")
                                }
                            }else{
                                _updateState.value = _updateState.value.copy(message = "Password did not changed, try later!")
                            }
                        }
                    }else{
                        _updateState.value = _updateState.value.copy(message = "Enter the correct password")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}