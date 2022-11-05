package com.bendg.bg.presenter.ui.fragment.faq

import androidx.navigation.fragment.findNavController
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentFaqBinding

class FAQFragment : BaseFragment<FragmentFaqBinding>(FragmentFaqBinding::inflate) {

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(FAQFragmentDirections.actionFAQFragmentToSettingsFragment())
        }
    }

    override fun init() {
    }

    override fun observers() {
    }
}