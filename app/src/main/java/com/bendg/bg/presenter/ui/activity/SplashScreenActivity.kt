package com.bendg.bg.presenter.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.bendg.bg.common.Constants
import com.bendg.bg.databinding.ActivitySplashScreenBinding
import com.bendg.bg.common.DataStore
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLogo()
    }

    private fun showLogo() {
        binding.ivLogo.apply {
            alpha = 0f
            animate().setDuration(1500).alpha(1f).withEndAction {
                initPreference()
                navigateToAuth()
            }
        }
    }

    private fun getPreferences() = DataStore.getPreferences()

    fun initPreference() {
        lifecycleScope.launch {
            getPreferences().collect {
                it.contains(stringPreferencesKey(Constants.KEY))
            }
        }
    }

    private fun navigateToAuth() {
        startActivity(Intent(this@SplashScreenActivity, AuthActivity::class.java))
        finish()
    }
}