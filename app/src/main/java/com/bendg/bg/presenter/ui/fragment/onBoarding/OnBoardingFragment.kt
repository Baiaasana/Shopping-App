package com.bendg.bg.presenter.ui.fragment.onBoarding

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bendg.bg.R
import com.bendg.bg.presenter.adapter.ViewPagerAdapter
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentOnBoardingBinding
import com.bendg.bg.presenter.ui.fragment.onBoarding.screens.FirstScreen
import com.bendg.bg.presenter.ui.fragment.onBoarding.screens.SecondScreen
import com.bendg.bg.presenter.ui.fragment.onBoarding.screens.ThirdScreen
import com.bendg.bg.common.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun listeners() {
    }
    override fun init() {
        hasFinished()

        val fragmentList = mutableListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
        )

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle, fragmentList)
        binding.viewPager2.adapter = adapter
    }

    override fun observers() {
    }

    private fun hasFinished(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPreferences().collect {
                    val k = it.contains(stringPreferencesKey(Constants.KEY))
                    if (it.contains(stringPreferencesKey(Constants.KEY))) {
                        Log.d("log", "$k")
                        findNavController().navigate(R.id.action_onBoardingFragment_to_logInFragment)
                    }
                }
            }
        }
    }
}