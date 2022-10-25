package com.bendg.bg.presenter.ui.fragment.onBoarding.screens

import androidx.viewpager2.widget.ViewPager2
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentSecondScreenBinding

class SecondScreen : BaseFragment<FragmentSecondScreenBinding>(FragmentSecondScreenBinding::inflate) {

    override fun listeners() {

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)

        binding.skip.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }

    override fun init() {
    }

    override fun observers() {
    }
}