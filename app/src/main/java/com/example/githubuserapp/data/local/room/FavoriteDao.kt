package com.example.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavoriteUser)

    @Update
    fun update(favUser: FavoriteUser)

    @Delete
    fun delete(favUser: FavoriteUser)

    @Query("SELECT  * from FavoriteUser")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser?>
}