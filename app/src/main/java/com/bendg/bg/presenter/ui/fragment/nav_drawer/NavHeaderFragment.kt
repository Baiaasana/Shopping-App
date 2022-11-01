package com.bendg.bg.presenter.ui.fragment.nav_drawer

import android.widget.Toast
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.NavHeaderBinding
import com.bendg.bg.presenter.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHeaderFragment :BaseFragment<NavHeaderBinding>(NavHeaderBinding::inflate) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    override fun listeners() {
    }

    override fun init() {
        showData()
    }

    override fun observers() {
    }

    private fun showData() {
        databaseReference.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfo = snapshot.getValue(UserModel::class.java) ?: return
                    binding.tvUsername.text = userInfo.userName
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }
            })
    }

}

