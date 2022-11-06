package com.bendg.bg.presenter.ui.fragment.onBoarding.screens

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentThirdScreenBinding
import com.bendg.bg.presenter.ui.fragment.onBoarding.OnBoardingViewModel
import com.bendg.bg.common.Constants
import kotlinx.coroutines.launch

class ThirdScreen : BaseFragment<FragmentThirdScreenBinding>(FragmentThirdScreenBinding::inflate) {

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun listeners() {
        binding.getStarted.setOnClickListener {
            saveState()
            findNavController().navigate(R.id.action_onBoardingFragment_to_logInFragment)
        }
    }

    override fun init() {
    }

    override fun observers() {
    }

    private fun saveState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.save(Constants.KEY, true)
            }
        }
    }
}