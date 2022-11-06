package com.bendg.bg.presenter.ui.fragment.onBoarding.screens

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.common.Constants
import com.bendg.bg.databinding.FragmentFirstScreenBinding
import com.bendg.bg.presenter.ui.fragment.onBoarding.OnBoardingViewModel
import kotlinx.coroutines.launch

class FirstScreen : BaseFragment<FragmentFirstScreenBinding>(FragmentFirstScreenBinding::inflate) {

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun listeners() {

         val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)

        binding.next.setOnClickListener {
            viewPager?.currentItem = 1
        }

        binding.skip.setOnClickListener {
            saveState()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_logInFragment)
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