package com.bendg.bg.presenter.ui.fragment.wallet

import androidx.lifecycle.ViewModel
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.utility.view_states.TransactionsViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WalletViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    private val _transactionsFlow = MutableStateFlow<TransactionsViewState>(TransactionsViewState())
    val transactionsFlow = _transactionsFlow.asStateFlow()

    fun showTransactions() {
        databaseReference.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInformation = snapshot.getValue(UserModel::class.java) ?: return
                    val list = userInformation.transactions
                    _transactionsFlow.value = _transactionsFlow.value.copy(data = list)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun updateUserInfo(list: MutableList<UserModel.Transaction>) {
        databaseReference.child(auth.currentUser?.uid!!).child("transactions").setValue(list)
    }

}