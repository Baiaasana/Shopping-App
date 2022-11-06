package com.bendg.bg.presenter.ui.fragment.profile

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.common.Constants.PICK_IMAGE_REQUEST
import com.bendg.bg.databinding.ChangeDialogBinding
import com.bendg.bg.databinding.FragmentProfileBinding
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.presenter.ui.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var bindingDialog: ChangeDialogBinding

    // For profile-image upload
    private var imageUri: Uri? = null
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storageReference =
        FirebaseStorage.getInstance().getReference("Images/${auth.currentUser?.uid!!}")

    override fun listeners() {
        binding.apply {
            ivChange.setOnClickListener {
                openDialog()
            }
            ivChangeImage.setOnClickListener {
                selectImage()
            }
        }
    }

    override fun init() {
        viewModel.showUserInfo()
        try {
            downloadImage()
        } catch (e: Exception) {
            Log.d("log", e.message.toString())
        }
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
                    etUsername.setText(it.userInfo.userName)
                }
            }
        }

        AlertDialog.Builder(context)
            .setView(bindingDialog.root)
            .setTitle("Change Personal Information")
            .setIcon(R.drawable.ic_change_white)
            .setPositiveButton("Change") { dialog, _ ->
                val firstName = bindingDialog.etFirstname.text.toString()
                val lastname = bindingDialog.etLastname.text.toString()
                val location = bindingDialog.etLocation.text.toString()
                val phoneNumber = bindingDialog.etPhone.text.toString()
                val username = bindingDialog.etUsername.text.toString()

                when {
                    isEmptyField() -> Snackbar.make(binding.root,
                        getString(R.string.empty_fields_error),
                        Snackbar.LENGTH_LONG)
                        .show()
                    else -> {
                        val newInfo = UserModel(firstName = firstName,
                            lastName = lastname,
                            userName = username,
                            location = location,
                            phone_number = phoneNumber)
                        viewModel.updateUserInfo(newInfo)
                        Toast.makeText(context, "User information is changed", Toast.LENGTH_SHORT)
                            .show()
                        viewModel.showUserInfo()
                        observers()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Images"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            imageUri = data.data
            binding.ivProfileImage.setImageURI(imageUri)
            (activity as MainActivity).disableNavBar()
            uploadImage()
        }
    }

    private fun uploadImage() {
        try {
            val bitmap = binding.ivProfileImage.drawable.toBitmap()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val putBytes = storageReference.putBytes(byteArray)
            val task = putBytes.continueWithTask {
                storageReference.downloadUrl
            }.addOnCompleteListener {
                imageUri = it.result
                Snackbar.make(binding.root, "image uploaded", Snackbar.LENGTH_SHORT).show()
                (activity as MainActivity).enableNavBar()
            }
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun downloadImage() {
        try {
            (activity as MainActivity).disableNavBar()
            val localFile: File = File.createTempFile("tempFile", "jpg")
            val uid = auth.currentUser?.uid!!
            val storageReference = FirebaseStorage.getInstance().getReference("Images/$uid")
            storageReference.getFile(localFile).addOnSuccessListener {
                binding.apply {
                    ivProfileImage.setImageBitmap(BitmapFactory.decodeFile(localFile.absolutePath))
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }
            (activity as MainActivity).enableNavBar()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isEmptyField(): Boolean = with(bindingDialog) {
        return@with etFirstname.text.toString().isEmpty() ||
                etUsername.text.toString().isEmpty() ||
                etPhone.text.toString().isEmpty() ||
                etLastname.text.toString().isEmpty() ||
                etLocation.text.toString().isEmpty()
    }

}