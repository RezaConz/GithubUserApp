package com.example.githubuserapp.ui.detail.tabs.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.ui.detail.tabs.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

  var username1: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_USERNAME, username1)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
    fun setUsername(username: String) {
        username1 = username
        notifyDataSetChanged()
    }
}