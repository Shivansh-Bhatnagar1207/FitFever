package com.example.vitalizeme.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.FragmentUserInfoBinding
import java.util.Date

class UserInfo : Fragment() {

    private lateinit var binding : FragmentUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(inflater)



        binding.loginbtn.setOnClickListener {
            val name = binding.etname.text.toString()
            val phone = binding.etphone.text.toString()
            val DOB = binding.etDOB.text.toString()
            val height =binding.etHeight.text.toString()
            val weight = binding.etWeight.text.toString()
            val checked = binding.radiogroup.checkedRadioButtonId
            val selected = binding.root.findViewById<RadioButton>(checked)

            if(name.isEmpty() ) {
                binding.nameLayout.error = "Please Enter Name"
            }else if(height.isEmpty() || weight.isEmpty()){
                binding.HeightLayout.error = "Please Enter Height"
            }



        }


        return binding.root
    }

}