package com.example.githubuserapp.ui.favorite.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.local.entity.FavoriteUser
import com.example.githubuserapp.databinding.GithubUserItemBinding
import com.example.githubuserapp.ui.detail.DetailActivity

class FavoriteAdapter:ListAdapter<FavoriteUser, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder (private val binding: GithubUserItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : FavoriteUser){
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .into(binding.ivProfilePicture)
            binding.tvName.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =GithubUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listUser = getItem(position)
        holder.bind(listUser)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.USER, listUser.login)
            intent.putExtra(DetailActivity.KEY_ID,listUser.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>(){
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}