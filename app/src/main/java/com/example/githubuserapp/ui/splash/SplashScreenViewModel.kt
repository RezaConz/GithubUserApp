package com.example.githubuserapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubuserapp.preference.SettingPreferences

class SplashScreenViewModel(private val pref: SettingPreferences):ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}