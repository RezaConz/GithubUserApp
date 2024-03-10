package com.example.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel(){
    private val favoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites() : LiveData<List<FavoriteUser>> = favoriteRepository.getAllFavorite()
}