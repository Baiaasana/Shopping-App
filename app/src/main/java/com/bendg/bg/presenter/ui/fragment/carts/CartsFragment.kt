package com.bendg.bg.presenter.ui.fragment.carts

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentCartsBinding
import com.bendg.bg.presenter.adapter.CartAdapter
import com.bendg.bg.utility.cartList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartsFragment : BaseFragment<FragmentCartsBinding>(FragmentCartsBinding::inflate) {

    private val cartsAdapter = CartAdapter()

    private var sum = cartList.sumOf { it.price!! }

    override fun listeners() {
        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(
                CartsFragmentDirections.actionCartsFragmentToCheckoutFragment(
                    totalMoney = binding.tvPrice.text.toString().toInt()
                )
            )
            for (x in cartList){
                x.counter = 1
            }
            sum = cartList.sumOf { it.price!! }
        }
    }

    override fun init() {
        initRecycle()
//        ItemTouchHelper(cartsSwipeCallback).attachToRecyclerView(binding.rvCarts)
        cartsAdapter.submitList(cartList)
        binding.tvPrice.text = sum.toString()

        cartsAdapter.setCallback(object : CartAdapter.Callback{
            override fun onPlusClick(itemID: Int) {
               plus(itemID)
                    var totalsum = 0
                    for(x in cartList){
                        sum = x.counter * x.price!!
                        totalsum += sum
                    }
                    binding.tvPrice.text = totalsum.toString()
            }

            override fun onMinusCLick(itemID: Int) {
                minus(itemID)
                var totalsum = 0
                for(x in cartList){
                    sum = x.counter * x.price!!
                    totalsum -= sum
                }
                var total = totalsum.toString()
                if(total.contains("-")){
                    total = total.toString().drop(1)
                    totalsum = total.toInt()
                }
                binding.tvPrice.text = totalsum.toString()
            }

//            override fun deleteClick(itemID: Int) {
//                removeItem(itemID)
//            }
        })
    }
//    private fun removeItem(itemID: Int){
//        Log.d("log", "old list - ".plus(cartList))
//        val item = cartList.find { item ->
//            item.id == itemID
//        }
//        val index = cartList.indexOf(item)
//        cartList.removeAt(index)
//        cartsAdapter.submitList(cartList.toList())
//        Log.d("log", "new list - ".plus(cartList))
//    }

    private fun minus(itemID:Int){
        val item = cartList.find { item ->
            item.id == itemID
        }
        val index = cartList.indexOf(item)
        cartList[index].counter--
        cartsAdapter.submitList(cartList.toList())
    }

    private fun plus(itemID: Int){
        val item = cartList.find { item ->
            item.id == itemID
        }
        val index = cartList.indexOf(item)
        cartList[index].counter++
        cartsAdapter.submitList(cartList.toList())
    }

    override fun observers() {
    }

    private fun initRecycle() {
        binding.rvCarts.apply {
            adapter = cartsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private val favoritesSwipeCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }

//    private val cartsSwipeCallback = object :
//        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder,
//        ): Boolean = false
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            val id = viewHolder.itemView.tag
//            viewLifecycleOwner.lifecycleScope.launch {
//
//                Log.d("log", "old list -".plus(cartList))
//                val item = cartList.find {
//                    it.id == id
//                }
//                Log.d("log", "new list item -".plus(item))
//                val index = cartList.indexOf(item)
//                Log.d("log", "new list index -".plus(index))
//                cartList.removeAt(index)
//                Log.d("log", "new list -".plus(cartList))
//                cartsAdapter.submitList(cartList)
//                cartsAdapter.notifyItemRangeRemoved(0, cartList.size)
//                val prevSize: Int = cartList.size
//                cartsAdapter.notifyItemRangeInserted(0, cartList.size - prevSize)
//            }
//        }
//    }

}