package com.example.githubuserapp.ui.favorite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.ui.favorite.adapter.FavoriteAdapter


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = FavoriteViewModelFactory.getInstance(application)
        val viewModel: FavoriteViewModel = ViewModelProvider(this,factory)[FavoriteViewModel::class.java]

        val adapter = FavoriteAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        viewModel.getAllFavorites().observe(this){list ->
            if (list != null){
                adapter.submitList(list)
                binding.rvFavorite.adapter = adapter
            }

            if (list.isEmpty()){
                Toast.makeText(
                    this,
                    "There are no favorite users",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}