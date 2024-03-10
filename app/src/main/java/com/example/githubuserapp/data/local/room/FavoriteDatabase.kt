package com.example.githubuserapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteDatabase: RoomDatabase(){

    abstract fun favDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "fav_db")
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }
}