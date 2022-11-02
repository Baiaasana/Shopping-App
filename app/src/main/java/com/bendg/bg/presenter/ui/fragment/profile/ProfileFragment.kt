package com.bendg.bg.presenter.ui.fragment.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    override fun listeners() {
//        binding.ivChange.setOnClickListener {
//
//
//
//        }
    }
    override fun init() {
        viewModel.showUserInfo()
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userInfo.collect{
                binding.apply {
                    tvEmail.text = it.userInfo!!.email.toString()
                    tvFirstName.text = it.userInfo.firstName.toString()
                    tvLastName.text = it.userInfo.lastName.toString()
                    tvUsername.text = it.userInfo.userName.toString()
                    tvLocation.text = it.userInfo.location.toString()
                    tvPhone.text = it.userInfo.phone_number.toString()
                }
            }
        }
    }

//    private fun changeObserve(){
//
//    }

}