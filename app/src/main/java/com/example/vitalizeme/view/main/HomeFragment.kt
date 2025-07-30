package com.example.vitalizeme.view.main

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.vitalizeme.R
import com.example.vitalizeme.adapter.BannerAdapter
import com.example.vitalizeme.databinding.FragmentHomeBinding
import com.example.vitalizeme.model.Users
import com.example.vitalizeme.repository.BannerRepository
import com.example.vitalizeme.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private lateinit var sp : SharedPreferences
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapter: BannerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()

        sp = requireContext().getSharedPreferences("User",MODE_PRIVATE)

        binding.height.text = sp.getString("height","N/A")
        binding.weight.text = sp.getString("weight","N/A")

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