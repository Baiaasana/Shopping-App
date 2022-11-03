package com.bendg.bg.presenter.ui.fragment.wallet

import androidx.fragment.app.viewModels
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {

    private val viewModel: WalletViewModel by viewModels()

    override fun listeners() {}

    override fun init() {
        binding.tvPrice.text =
            viewModel.getMoney().toString()


    }

    override fun observers() {}

}