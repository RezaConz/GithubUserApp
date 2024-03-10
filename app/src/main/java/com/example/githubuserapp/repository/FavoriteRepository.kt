package com.example.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.data.local.room.FavoriteDao
import com.example.githubuserapp.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {

    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavDao.getAllFavorite()

    fun getUserFavoriteByUsername(username:String): LiveData<FavoriteUser?> =
        mFavDao.getFavoriteUserByUsername(username)


    fun insert(favUser: FavoriteUser) {
        executorService.execute { mFavDao.insert(favUser) }
    }

    fun delete(favUser: FavoriteUser) {
        executorService.execute { mFavDao.delete(favUser) }
    }
}