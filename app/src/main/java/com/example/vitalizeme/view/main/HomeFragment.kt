package com.example.vitalizeme.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.vitalizeme.R
import com.example.vitalizeme.adapter.BannerAdapter
import com.example.vitalizeme.databinding.FragmentHomeBinding
import com.example.vitalizeme.viewmodel.BannerRepository
import com.example.vitalizeme.viewmodel.HomeViewModel


class HomeFragment : Fragment() {


    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapter: BannerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val repo = BannerRepository()
        val viewModel = HomeViewModel(repo)

        adapter = BannerAdapter(emptyList())


        binding.VP.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner){
            banners ->
            adapter.bannerItems = banners
        }


        return binding.root
    }

}