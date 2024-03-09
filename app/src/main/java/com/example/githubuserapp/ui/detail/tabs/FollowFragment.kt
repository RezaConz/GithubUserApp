package com.example.githubuserapp.ui.detail.tabs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.Items
import com.example.githubuserapp.databinding.FragmentFollowBinding
import com.example.githubuserapp.ui.detail.tabs.adapter.FollowAdapter

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val followViewModel: FollowViewModel by viewModels()

    companion object {
        const val ARG_POSITION = "position"
        const val  ARG_USERNAME = "username"
    }

    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvListFollow.addItemDecoration(itemDecoration)

        followViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let { userData ->
                setGithubData(userData)
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)?:""
        }
        if (position == 1){
            followViewModel.setFollowers(username)
        } else {
            followViewModel.setFollowing(username)
        }
    }

    private fun setGithubData(item: List<Items>) {
        val adapter = FollowAdapter()
        adapter.submitList(item)
        binding.rvListFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}