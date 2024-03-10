package com.example.githubuserapp.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.data.remote.response.DetailUserResponse
import com.example.githubuserapp.data.remote.response.GithubResponse
import com.example.githubuserapp.data.remote.response.Items
import com.example.githubuserapp.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepository private constructor(
    private val apiService: ApiService
){
    fun findUsers(search:String) {
        _isLoading.value = true
        val client = apiService.getGithub(search)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.items
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun detailUser(receivedData:String) {
        _isLoading.value = true
        val client = apiService.getDetailUser(receivedData)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private val _listUser = MutableLiveData<List<Items>>()
    val listUser: LiveData<List<Items>> = _listUser

    private var _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: MutableLiveData<DetailUserResponse?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        @Volatile
        private var instance: GithubRepository? = null
        fun getInstance(
            apiService: ApiService
        ): GithubRepository =
            instance ?: synchronized(this) {
                instance ?: GithubRepository(apiService)
            }.also { instance = it }
    }
}