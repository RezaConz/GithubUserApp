package com.example.githubuserapp.data.retrofit

import com.example.githubuserapp.data.response.DetailUserResponse
import com.example.githubuserapp.data.response.GithubResponse
import com.example.githubuserapp.data.response.Items
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getGithub(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<Items>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<Items>>
}