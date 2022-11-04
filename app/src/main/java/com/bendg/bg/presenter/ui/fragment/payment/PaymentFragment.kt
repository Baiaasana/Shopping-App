package com.bendg.bg.presenter.ui.fragment.payment

import androidx.navigation.fragment.findNavController
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {
    override fun listeners() {
        binding.btnBackToHome.setOnClickListener {
            findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToHomeFragment())
        }
    }
    override fun init() {
    }
    override fun observers() {
    }
}