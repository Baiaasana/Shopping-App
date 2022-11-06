package com.bendg.bg.domain.use_case

import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.domain.repository.OrdersRepository
import javax.inject.Inject

class OrdersUseCase @Inject constructor(
    private val repository: OrdersRepository
) {

    suspend fun getOrders(): List<OrderedProduct>{
        return repository.getOrders()
    }

    suspend fun addOrder(order: OrderedProduct){
        repository.addOrders(order)
    }

    suspend fun removeOrder(order: OrderedProduct){
        repository.removeOrders(order)
    }

}