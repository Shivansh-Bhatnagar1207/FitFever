package com.example.vitalizeme.view.main.plans

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.vitalizeme.R
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.constants.WORKOUTDATA
import com.example.vitalizeme.databinding.FragmentStretchingBinding
import com.example.vitalizeme.model.Stretchs
import com.example.vitalizeme.repository.stretchingRepository
import com.example.vitalizeme.viewmodel.StretchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StretchingFragment : Fragment() {

    private lateinit var vm: StretchViewModel
    private lateinit var binding: FragmentStretchingBinding

    private lateinit var sp: SharedPreferences

    private var st: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStretchingBinding.inflate(inflater)

        sp = requireContext().getSharedPreferences(PrefConstants.WORKOUT, MODE_PRIVATE)

        var workoutTime = sp.getInt(WORKOUTDATA.TIME, 0)
        var workoutCount = sp.getInt(WORKOUTDATA.WORKOUT_COUNT, 0)
        var KcalCount = sp.getInt(WORKOUTDATA.KCAL_COUNT, 0)


        val repo = stretchingRepository()
        vm = StretchViewModel(repo)

        vm.data.observe(viewLifecycleOwner) { stretchs ->
            st?.cancel()
            binding.stretchingName.text = stretchs.title
            binding.stretchingDesc.text = stretchs.desc
            binding.stretchImage.setImageResource(stretchs.img)
            if (stretchs.pos < 5) {
                binding.stretchingCounter.text = " stretches ${stretchs.pos + 1}"
                st = startTimer()
            } else {
                Toast.makeText(requireContext(), "Finished stretching", Toast.LENGTH_SHORT).show()
                binding.skipbtn.isVisible = false
                binding.stretchingCounter.isVisible = false
                binding.timer.isVisible = false
                workoutCount++
                workoutTime += 150
                KcalCount += 15

                sp.edit().apply {
                    putInt(WORKOUTDATA.KCAL_COUNT, KcalCount)
                    putInt(WORKOUTDATA.WORKOUT_COUNT, workoutCount)
                    putInt(WORKOUTDATA.TIME, workoutTime)
                    apply()
                }
            }
        }

        binding.skipbtn.setOnClickListener {
            st?.cancel()
            st = startTimer()
            vm.nextCard()
        }

        binding.exitbtn.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }


    private fun startTimer(): Job {
        return lifecycleScope.launch {
            var count = 30

            while (count >= 0) {
                binding.timer.text = "$count sec"
                delay(1000)
                count--
            }
            vm.nextCard()
        }
    }

}


