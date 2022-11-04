package com.bendg.bg.presenter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.bendg.bg.common.Constants
import com.bendg.bg.databinding.ActivitySplashScreenBinding
import com.bendg.bg.utility.DataStore
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivLogo.apply {
            alpha = 0f
            animate().setDuration(1500).alpha(1f).withEndAction {
                init()
                startActivity(Intent(this@SplashScreenActivity, AuthActivity::class.java))
                finish()
            }
        }
    }

    private fun getPreferences() = DataStore.getPreferences()

    fun init() {
        lifecycleScope.launch {
            getPreferences().collect {
                val isSkipped = it.contains(stringPreferencesKey(Constants.KEY))
            }
        }
    }
}