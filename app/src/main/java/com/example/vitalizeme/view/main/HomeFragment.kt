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
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.constants.USERDATA
import com.example.vitalizeme.constants.WORKOUTDATA
import com.example.vitalizeme.databinding.FragmentHomeBinding
import com.example.vitalizeme.model.Users
import com.example.vitalizeme.repository.BannerRepository
import com.example.vitalizeme.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private lateinit var userSP : SharedPreferences
    private lateinit var workoutSP : SharedPreferences
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

        userSP = requireContext().getSharedPreferences(PrefConstants.USER,MODE_PRIVATE)
        workoutSP = requireContext().getSharedPreferences(PrefConstants.WORKOUT,MODE_PRIVATE)


        val height :Double? = userSP.getString(USERDATA.HEIGHT,"")?.toDouble()
        val weight : Double? = userSP.getString(USERDATA.WEIGHT,"")?.toDouble()
        binding.height.text = "${height.toString()} m"
        binding.weight.text = "${weight.toString()} kg"


        val time = workoutSP.getInt(WORKOUTDATA.TIME,0)
        val workoutCount = workoutSP.getInt(WORKOUTDATA.WORKOUT_COUNT,0)
        val KcalBurned = workoutSP.getInt(WORKOUTDATA.KCAL_COUNT,0)

        binding.min.text = (time/60).toString()
        binding.kcal.text = KcalBurned.toString()
        binding.workout.text = workoutCount.toString()


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