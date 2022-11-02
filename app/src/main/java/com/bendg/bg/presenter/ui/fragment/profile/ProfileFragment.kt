package com.bendg.bg.presenter.ui.fragment.profile

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.ChangeDialogBinding
import com.bendg.bg.databinding.FragmentProfileBinding
import com.bendg.bg.presenter.model.UserModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var bindingDialog: ChangeDialogBinding

    private val viewModel: ProfileViewModel by viewModels()
    override fun listeners() {
        binding.ivChange.setOnClickListener {
            openDialog()
        }
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

    private fun openDialog() {
        bindingDialog = ChangeDialogBinding.inflate(LayoutInflater.from(context))
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userInfo.collect {
                bindingDialog.apply {
                    etFirstname.setText(it.userInfo!!.firstName)
                    etLastname.setText(it.userInfo.lastName)
                    etPhone.setText(it.userInfo.phone_number)
                    etLocation.setText(it.userInfo.location)
                }
            }
        }

        AlertDialog.Builder(context)
            .setView(bindingDialog.root)
            .setTitle("Change Personal Information")
            .setIcon(R.drawable.ic_baseline_settings_24)
            .setPositiveButton("Change") { dialog, _ ->
                val firstName = bindingDialog.etFirstname.text.toString()
                val lastname = bindingDialog.etLastname.text.toString()
                val location = bindingDialog.etLocation.text.toString()
                val phoneNumber = bindingDialog.etPhone.text.toString()

                val newInfo = UserModel(firstName = firstName,
                    lastName = lastname,
                    location = location,
                    phone_number = phoneNumber)
                viewModel.updateUserInfo(newInfo)
                Toast.makeText(context, "User information is changed", Toast.LENGTH_SHORT)
                    .show()
                viewModel.showUserInfo()
                observers()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
        true
    }
}