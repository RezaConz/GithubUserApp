package com.example.githubuserapp.ui.detail.tabs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.remote.response.Items
import com.example.githubuserapp.databinding.GithubUserItemBinding

class FollowAdapter: ListAdapter<Items, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder (private val binding: GithubUserItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Items){
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .into(binding.ivProfilePicture)
            binding.tvName.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GithubUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listUser = getItem(position)
        holder.bind(listUser)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Items>(){
            override fun areItemsTheSame(
                oldItem: Items,
                newItem: Items
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Items,
                newItem: Items
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}