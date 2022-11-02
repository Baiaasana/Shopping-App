package com.bendg.bg.presenter.ui.fragment.settings.email_support

import androidx.navigation.fragment.findNavController
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentSupportBinding

class SupportFragment : BaseFragment<FragmentSupportBinding>(FragmentSupportBinding::inflate) {

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(SupportFragmentDirections.actionSupportFragmentToSettingsFragment())
        }
    }

    override fun init() {
    }

    override fun observers() {
    }
}