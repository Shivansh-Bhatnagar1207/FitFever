package com.example.vitalizeme.view.main.plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.FragmentCardioBinding


class CardioFragment : Fragment() {
    private lateinit var binding: FragmentCardioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardioBinding.inflate(inflater)
        // Inflate the layout for this fragment

        return binding.root
    }



}