package com.bendg.bg.presenter.ui.fragment.settings

import androidx.navigation.fragment.findNavController
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun listeners() {
        binding.llChangePassword.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToChangePasswordFragment())
        }
        binding.llFaq.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToFAQFragment())
        }

        binding.llEmailSupport.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToSupportFragment())
        }
    }

    override fun init() {
    }

    override fun observers() {
    }

}