package com.bendg.bg.presenter.ui.fragment.checkout

import androidx.lifecycle.ViewModel
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.domain.use_case.OrdersUseCase
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.extensions.view_states.TransactionsViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val useCase: OrdersUseCase,
) : ViewModel() {

    suspend fun addOrder(order: OrderedProduct){
        useCase.addOrder(order = order)
    }
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
}