package com.example.githubuserapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.DetailUserResponse
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.ui.detail.tabs.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    val detailViewModel: DetailViewModel by viewModels()
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

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.user.observe(this){
            setDetailData(it!!)
        }

        detailViewModel.detailUser(receivedData.toString())

        receivedData?.let { getViewPager(it) }
    }

    fun setDetailData(responseBody: DetailUserResponse){
        binding.tvNameDetail.text = responseBody.name
        binding.tvUsername.text = responseBody.login
        Glide.with(this@DetailActivity)
            .load(responseBody.avatarUrl)
            .into(binding.ivImageProfile)
        binding.tvFollowers.text = "${responseBody.followers} Followers"
        binding.tvFollowing.text = "${responseBody.following} Following"
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