package com.bendg.bg.presenter.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bendg.bg.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAuth.getInstance().currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_auth)
    }
}

