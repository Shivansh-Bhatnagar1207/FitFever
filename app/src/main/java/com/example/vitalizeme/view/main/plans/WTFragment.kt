package com.example.vitalizeme.view.main.plans

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalizeme.R
import com.example.vitalizeme.adapter.PlansAdapter
import com.example.vitalizeme.adapter.WTAdapter
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.constants.WORKOUTDATA
import com.example.vitalizeme.databinding.FragmentWTBinding
import com.example.vitalizeme.repository.WTRepository
import com.example.vitalizeme.viewmodel.WTViewModel


class WTFragment : Fragment() {
    private lateinit var binding: FragmentWTBinding
    private lateinit var adapter: WTAdapter
    private lateinit var rcv: RecyclerView
    private lateinit var sp: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWTBinding.inflate(inflater)

        sp = requireContext().getSharedPreferences(PrefConstants.WORKOUT, MODE_PRIVATE)
        var workoutCount = sp.getInt(WORKOUTDATA.WORKOUT_COUNT, 0)
        var kCal = sp.getInt(WORKOUTDATA.KCAL_COUNT, 0)
        var time = sp.getInt(WORKOUTDATA.TIME, 0)
        rcv = binding.wtrcv
        val repo = WTRepository()
        val vm = WTViewModel(repo)
        adapter = WTAdapter(
            mutableListOf(),
            { item, pos ->
                item.totalCal += item.calPerSet
                item.timeSpent += 120
                item.set++
                adapter.notifyItemChanged(pos)
            },
            { item, pos ->
                if(item.timeSpent !=0){
                time += item.timeSpent
                kCal += item.totalCal
                workoutCount++
                item.totalCal = 0
                item.timeSpent = 0
                item.set = 0
                sp.edit().apply {
                    putInt(WORKOUTDATA.TIME, time)
                    putInt(WORKOUTDATA.KCAL_COUNT, kCal)
                    putInt(WORKOUTDATA.WORKOUT_COUNT, workoutCount)
                    apply()
                }
                Toast.makeText(requireContext(), "Workout Recorded", Toast.LENGTH_SHORT).show()
            }else
            {
                Toast.makeText(requireContext(), "Please Start workout", Toast.LENGTH_SHORT).show()
            }})

        rcv.adapter = adapter

        rcv.layoutManager = LinearLayoutManager(requireContext())
        vm.data.observe(viewLifecycleOwner) { wTS ->
            adapter.WTList.clear()
            adapter.WTList.addAll(wTS)
            adapter.notifyDataSetChanged()

        }

        return binding.root
    }
}