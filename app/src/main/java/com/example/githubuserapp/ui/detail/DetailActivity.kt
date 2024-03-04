package com.example.githubuserapp.ui.detail

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.DetailUserResponse
import com.example.githubuserapp.data.retrofit.ApiConfig
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.ui.detail.tabs.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    var receivedData: String? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedIntent = intent

        if (receivedIntent != null && receivedIntent.hasExtra("USER")) {
            receivedData = receivedIntent.getStringExtra("USER").toString()
        }
        detailUser()

        receivedData?.let { getViewPager(it) }

    }

    private fun detailUser() {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(receivedData.toString())
        client.enqueue(object : Callback<DetailUserResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.tvNameDetail.text = responseBody.name
                        binding.tvUsername.text = responseBody.login
                        Glide.with(this@DetailActivity)
                            .load(responseBody.avatarUrl)
                            .into(binding.ivImageProfile)
                        binding.tvFollowers.text = "${responseBody.followers} Followers"
                        binding.tvFollowing.text = "${responseBody.following} Following"
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getViewPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.setUsername(username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
}