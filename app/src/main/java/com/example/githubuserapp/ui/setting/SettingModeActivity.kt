package com.example.githubuserapp.ui.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.databinding.ActivitySettingModeBinding
import com.example.githubuserapp.preference.SettingPreferences
import com.example.githubuserapp.preference.dataStore

class SettingModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, SettingModeViewModelFactory(pref))[SettingModeViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swThemeButton.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swThemeButton.isChecked = false
            }
        }

        binding.swThemeButton.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}