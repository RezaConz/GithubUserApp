package com.example.githubuserapp.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.remote.response.DetailUserResponse
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import com.example.githubuserapp.repository.FavoriteRepository
import com.example.githubuserapp.repository.GithubRepository

class DetailViewModel(application: Application) :ViewModel(){

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private val apiService = ApiConfig.getApiService()

    private val githubRepository: GithubRepository = GithubRepository.getInstance(apiService)
    fun insert(favUser: FavoriteUser) {
        mFavoriteRepository.insert(favUser)
    }

    fun delete(favUser: FavoriteUser) {
        mFavoriteRepository.delete(favUser)
    }

    fun getFavoriteByUsername(username: String): LiveData<FavoriteUser?> {
        return mFavoriteRepository.getUserFavoriteByUsername(username)
    }

    fun detailUser(search:String) = githubRepository.detailUser(search)

    fun loading(): LiveData<Boolean> = githubRepository.isLoading

    fun user():MutableLiveData<DetailUserResponse?> = githubRepository.detailUser

}