package com.bendg.bg.presenter.ui.fragment.change_password

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(FragmentChangePasswordBinding::inflate) {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun listeners() {
        changeListener()
        backListener()
    }

    private fun changeListener() {
        binding.btnChange.setOnClickListener {
            viewModel.updatePassword(binding.etOldPassword.text.toString(),
                binding.etNewPassword.text.toString())
            observer()
        }
    }

    private fun backListener() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(ChangePasswordFragmentDirections.actionChangePasswordFragmentToSettingsFragment())
        }
    }

    override fun init() {
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateState.collectLatest {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun observers() {
    }
}