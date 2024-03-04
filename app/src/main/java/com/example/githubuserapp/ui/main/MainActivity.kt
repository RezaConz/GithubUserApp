package com.example.githubuserapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.GithubAdapter
import com.example.githubuserapp.data.response.Items
import com.example.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUserItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserItem.addItemDecoration(itemDecoration)

        mainViewModel.user.observe(this) { user ->
            setReviewData(user)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.findUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setReviewData(user: List<Items>) {
        val adapter = GithubAdapter()
        adapter.submitList(user)
        binding.rvUserItem.adapter = adapter
    }
}