package com.example.githubuserapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.ui.main.adapter.GithubAdapter
import com.example.githubuserapp.data.remote.response.Items
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.preference.SettingPreferences
import com.example.githubuserapp.ui.favorite.FavoriteActivity
import com.example.githubuserapp.ui.setting.SettingModeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUserItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserItem.addItemDecoration(itemDecoration)

        val pref = SettingPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]


        modeCheck()

        viewModel.getItems("reza")

        viewModel.user().observe(this){
            setReviewData(it)
        }

        viewModel.loading().observe(this){
            showLoading(it)
        }


        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.getItems(searchView.text.toString())
                    viewModel.user().observe(this@MainActivity){
                        setReviewData(it)
                    }
                    false
                }
        }

        binding.searchBar.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.menu1 ->{
                    val intent = Intent(this,FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu2 ->{
                    val intent = Intent(this,SettingModeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun modeCheck() {
        viewModel.getThemeSettings().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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