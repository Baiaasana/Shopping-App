package com.bendg.bg.presenter.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bendg.bg.R
import com.bendg.bg.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
        listeners()
    }

    private fun setUpNavigation() {
        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.nav_host_fragment_main))
        binding.navView.setupWithNavController(findNavController(R.id.nav_host_fragment_main))
    }

    private fun listeners() {
        binding.logOutView.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    fun disableNavBar() {
        binding.bottomNavigationView.menu.forEach {
            it.isEnabled = false
        }
    }

    fun enableNavBar() {
        binding.bottomNavigationView.menu.forEach {
            it.isEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}