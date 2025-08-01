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
                Runningjob = startTracking(
                    runningIsActive,
                    { runningTime },
                    { runningTime = it },
                    { binding.runningText.text = it },
                    0.25
                )

            } else {
                binding.runningPlaybtn.setImageResource(R.drawable.play_btn)
            }
        }

        binding.runningStopbtn.setOnClickListener {
            stopTracking(Runningjob, runningTime, 0.25)
            runningTime = 0
            binding.runningText.text = "Burn Rate : 15 kcal/min"
            Toast.makeText(requireContext(), "Activity Recorded", Toast.LENGTH_SHORT).show()
        }

        binding.cyclingPlaybtn.setOnClickListener {
            cyclingIsActive = !cyclingIsActive
            if (cyclingIsActive) {
                binding.cyclingPlaybtn.setImageResource(R.drawable.pause_button)
                Cyclingjob = startTracking(
                    cyclingIsActive,
                    { cyclingTime },
                    { cyclingTime = it },
                    { binding.cyclingText.text = it },
                    0.18
                )
            } else {
                binding.cyclingPlaybtn.setImageResource(R.drawable.play_btn)
            }
        }

        binding.cyclingStopbtn.setOnClickListener {
            stopTracking(Cyclingjob, cyclingTime, 0.18)
            cyclingTime = 0
            binding.cyclingText.text = "Burn Rate : 10 Kcal/min"
            Toast.makeText(requireContext(), "Activity Recorded", Toast.LENGTH_SHORT).show()
        }

        binding.jumpingPlaybtn.setOnClickListener {
            jumpingIsActive = !jumpingIsActive
            if (jumpingIsActive) {
                binding.jumpingPlaybtn.setImageResource(R.drawable.pause_button)
                Jumpingjob = startTracking(
                    jumpingIsActive,
                    { jumpingTime },
                    { jumpingTime = it },
                    { binding.jumpingText.text = it },
                    0.15
                )
            } else {
                binding.jumpingPlaybtn.setImageResource(R.drawable.play_btn)
            }
        }

        binding.jumpingStopbtn.setOnClickListener {
            stopTracking(Jumpingjob, jumpingTime, 0.13)
            jumpingTime = 0
            binding.jumpingText.text = "Burn Rate : 08 Kcal/min"
            Toast.makeText(requireContext(), "Activity Recorded", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }


    private fun startTracking(
        isActive: Boolean,
        getTimer: () -> Int,
        setTimer: (Int) -> Unit,
        setText: (String) -> Unit,
        calBurned: Double
    ): Job {
        return lifecycleScope.launch {
            while (isActive) {
                delay(1000)
                var timer = getTimer() + 1
                setTimer(timer)
                val min = timer / 60
                val sec = timer % 60
                val timeFormatter = String.format("%2dm %2ds", min, sec)
                setText(
                    "time : $timeFormatter\n Calories : ${timer * calBurned} Kcal"
                )
            }
        }
    }

    private fun stopTracking(
        job: Job?,
        activityTime: Int,
        calBurned: Double
    ) {
        time = activityTime + time
        workoutCount++
        KcalCount = (activityTime * calBurned).toInt()

        job?.cancel()

        sp.edit().apply {
            putInt(WORKOUTDATA.TIME, time)
            putInt(WORKOUTDATA.WORKOUT_COUNT, workoutCount)
            putInt(WORKOUTDATA.KCAL_COUNT, KcalCount)
            apply()
        }
    }
}