package com.bendg.bg.presenter.ui.fragment.profile

import androidx.lifecycle.ViewModel
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.utility.view_states.AuthenticationViewState
import com.bendg.bg.utility.view_states.UserInfoState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    private val _userInfo = MutableStateFlow<UserInfoState>(UserInfoState())
    val userInfo = _userInfo.asStateFlow()

    fun showUserInfo() {
        databaseReference.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInformation = snapshot.getValue(UserModel::class.java) ?: return
                    _userInfo.value = _userInfo.value.copy(userInfo = userInformation)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

//    fun updateUserInfo(NewUserInfo: UserModel){
//
//    }

}