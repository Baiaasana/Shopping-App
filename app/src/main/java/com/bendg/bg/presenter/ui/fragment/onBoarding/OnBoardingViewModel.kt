package com.bendg.bg.presenter.ui.fragment.onBoarding

import androidx.lifecycle.ViewModel
import com.bendg.bg.common.DataStore

class OnBoardingViewModel : ViewModel() {

    suspend fun save(key: String, value: Boolean) {
        DataStore.save(key, value)
    }

    fun getPreferences() = DataStore.getPreferences()
}