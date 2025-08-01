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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vitalizeme.R
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.constants.WORKOUTDATA
import com.example.vitalizeme.databinding.FragmentCardioBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CardioFragment : Fragment() {
    private lateinit var binding: FragmentCardioBinding
    private lateinit var sp: SharedPreferences
    private var Runningjob: Job? = null
    private var Cyclingjob: Job? = null
    private var Jumpingjob: Job? = null
    private var runningIsActive = false
    private var cyclingIsActive = false
    private var jumpingIsActive = false
    private var runningTime = 0
    private var cyclingTime = 0
    private var jumpingTime = 0

    private var time = 0
    private var workoutCount = 0
    private var KcalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardioBinding.inflate(inflater)

        sp = requireContext().getSharedPreferences(PrefConstants.WORKOUT, MODE_PRIVATE)


        time = sp.getInt(WORKOUTDATA.TIME, 0)
        workoutCount = sp.getInt(WORKOUTDATA.WORKOUT_COUNT, 0)
        KcalCount = sp.getInt(WORKOUTDATA.KCAL_COUNT, 0)



        binding.runningPlaybtn.setOnClickListener {
            runningIsActive = !runningIsActive
            if (runningIsActive) {
                binding.runningPlaybtn.setImageResource(R.drawable.pause_button)
                startRunning()

            } else {
                binding.runningPlaybtn.setImageResource(R.drawable.play_btn)
            }
        }

        binding.runningStopbtn.setOnClickListener {
            stopDoing(runningTime, 0.25)
            Runningjob?.cancel()
            binding.runningText.text = "Burn Rate : 15 kcal/min"
            Toast.makeText(requireContext(), "Activity Recorded", Toast.LENGTH_SHORT).show()
        }

        binding.cyclingPlaybtn.setOnClickListener {
            cyclingIsActive = !cyclingIsActive
            if (cyclingIsActive) {
                binding.cyclingPlaybtn.setImageResource(R.drawable.pause_button)
                startCycling()
            } else {
                binding.cyclingPlaybtn.setImageResource(R.drawable.play_btn)
            }
        }

        binding.cyclingStopbtn.setOnClickListener {
            stopDoing(cyclingTime, 0.16)
            Cyclingjob?.cancel()
            binding.cyclingText.text = "Burn Rate : 10 Kcal/min"
            Toast.makeText(requireContext(), "Activity Recorded", Toast.LENGTH_SHORT).show()
        }

        binding.jumpingPlaybtn.setOnClickListener {
            jumpingIsActive = !jumpingIsActive
            if (jumpingIsActive) {
                binding.jumpingPlaybtn.setImageResource(R.drawable.pause_button)
                startJumping()
            } else {
                binding.jumpingPlaybtn.setImageResource(R.drawable.play_btn)
            }
        }

        binding.jumpingStopbtn.setOnClickListener {
            stopDoing(jumpingTime, 0.13)
            Jumpingjob?.cancel()
            binding.jumpingText.text = "Burn Rate : 08 Kcal/min"
            Toast.makeText(requireContext(), "Activity Recorded", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

    private fun startJumping() {
        Jumpingjob = lifecycleScope.launch {
            while (jumpingIsActive) {
                delay(1000)
                jumpingTime++
                val min = jumpingTime / 60
                val sec = jumpingTime % 60
                val timeFormatter = String.format("%2dm %2ds", min, sec)
                binding.jumpingText.text =
                    "time : $timeFormatter\n Calories : ${jumpingTime * 0.25} Kcal"
            }
        }
    }


    private fun startCycling() {
        Cyclingjob = lifecycleScope.launch {
            while (cyclingIsActive) {
                delay(1000)
                cyclingTime++
                val min = cyclingTime / 60
                val sec = cyclingTime % 60
                val timeFormatter = String.format("%2dm %2ds", min, sec)
                binding.cyclingText.text =
                    "time : $timeFormatter\n Calories : ${cyclingTime * 0.25} Kcal"
            }
        }
    }


    private fun startRunning() {
        Runningjob = lifecycleScope.launch {
            while (runningIsActive) {
                delay(1000)
                runningTime++
                val min = runningTime / 60
                val sec = runningTime % 60
                val timeFormatter = String.format("%2dm %2ds", min, sec)
                binding.runningText.text =
                    "time : $timeFormatter\n Calories : ${runningTime * 0.25} Kcal"
            }
        }

    }

    private fun stopDoing(activityTime: Int, calBurned: Double) {
        time = activityTime + time
        workoutCount++
        KcalCount = (activityTime * calBurned).toInt()
        sp.edit().apply {
            putInt(WORKOUTDATA.TIME, time)
            putInt(WORKOUTDATA.WORKOUT_COUNT, workoutCount)
            putInt(WORKOUTDATA.KCAL_COUNT, KcalCount)
            apply()
        }
    }
}