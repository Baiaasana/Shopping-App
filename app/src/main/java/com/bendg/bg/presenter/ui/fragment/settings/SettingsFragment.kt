package com.bendg.bg.presenter.ui.fragment.settings

import android.content.Intent
import android.provider.Settings.ACTION_HOME_SETTINGS
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

        binding.llLanguages.setOnClickListener {
            startActivity(Intent(ACTION_HOME_SETTINGS))
        }
    }

    override fun init() {
    }

    override fun observers() {
    }

}