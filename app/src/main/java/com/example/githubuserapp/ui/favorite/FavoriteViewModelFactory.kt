package com.example.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteViewModelFactory (private val application: Application) :

    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null
        fun getInstance(application: Application): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(application)
            }.also { instance = it }
    }
}
