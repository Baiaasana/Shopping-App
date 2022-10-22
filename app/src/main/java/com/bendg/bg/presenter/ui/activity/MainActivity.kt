package com.bendg.bg.presenter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bendg.bg.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}