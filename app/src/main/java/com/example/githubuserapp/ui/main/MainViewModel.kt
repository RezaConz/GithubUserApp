package com.example.githubuserapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubuserapp.data.remote.response.Items
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import com.example.githubuserapp.preference.SettingPreferences
import com.example.githubuserapp.repository.GithubRepository

class MainViewModel(private val pref: SettingPreferences) :ViewModel() {

    private val apiService = ApiConfig.getApiService()

    private val githubRepository: GithubRepository = GithubRepository(apiService)
    fun getItems(search:String) = githubRepository.findUsers(search)

    fun user():LiveData<List<Items>> = githubRepository.listUser

    fun loading():LiveData<Boolean> = githubRepository.isLoading

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}