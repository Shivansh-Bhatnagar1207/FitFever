package com.example.vitalizeme.view.main

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
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

        firestore = FirebaseFirestore.getInstance()

        sp = requireContext().getSharedPreferences("User",MODE_PRIVATE)

        val height :Double? = sp.getString("height","")?.toDouble()
        val weight : Double? = sp.getString("weight","")?.toDouble()
        binding.height.text = "${height.toString()} m"
        binding.weight.text = "${weight.toString()} kg"



        fun Bmical(height : Double,weight : Double) : Double
        {
            val bmi : Double = weight/(height*height)
            return String.format("%.2f",bmi).toDouble()
        }

        val bmi = Bmical(height!!,weight!!)
        binding.BMIText.text = bmi.toString()

        if(bmi <18.5){
            binding.level.text = "Under Weight"
            binding.BMIText.setTextColor(getColor(requireContext(),R.color.red))
            binding.level.setTextColor(getColor(requireContext(),R.color.red))
        }
        else if(bmi <= 24.9){
            binding.level.text = "Normal Weight"
            binding.level.setTextColor(getColor(requireContext(),R.color.green))
            binding.BMIText.setTextColor(getColor(requireContext(),R.color.green))
        }
        else if(bmi <= 25.0){
            binding.level.text = "Over Weight"
            binding.level.setTextColor(getColor(requireContext(),R.color.primary))
            binding.BMIText.setTextColor(getColor(requireContext(),R.color.primary))
        }else{
            binding.level.text = "Obese"
        }


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