package com.bendg.bg.presenter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bendg.bg.R
import com.bendg.bg.common.Constants
import com.bendg.bg.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment?
        val navView = binding.bottomNavigationView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.ordersFragment,
                R.id.favoritesFragment,
                R.id.profileFragment
            )
        )

        if (navHostFragment != null) {
            val navController = navHostFragment.navController
            // Setup NavigationUI
            setupActionBarWithNavController(navController,
                appBarConfiguration)
            navView.setupWithNavController(navController)

            findViewById<NavigationView>(R.id.nav_view)
                .setupWithNavController(navController)
        }

        binding.logOutView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}