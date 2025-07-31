package com.example.vitalizeme.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalizeme.R
import com.example.vitalizeme.adapter.PlansAdapter
import com.example.vitalizeme.databinding.FragmentPlanBinding
import com.example.vitalizeme.repository.PlanRepository
import com.example.vitalizeme.viewmodel.PlanViewModel


class PlanFragment : Fragment() {

    private lateinit var binding: FragmentPlanBinding
    private lateinit var adapter: PlansAdapter
    private lateinit var viewModel: PlanViewModel
    private lateinit var rcv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanBinding.inflate(layoutInflater)
        rcv = binding.plansRCV

        val repo = PlanRepository()
        viewModel = PlanViewModel(repo)

        adapter = PlansAdapter(
            emptyList(),
            { plans ->
                if (plans.planTitle == "Cardio") {
                    findNavController().navigate(R.id.action_planFragment_to_cardioFragment)
                }else if (plans.planTitle == "Meditation"){
                    findNavController().navigate(R.id.action_planFragment_to_meditationFragment)
                }else if (plans.planTitle == "Stretching"){
                    findNavController().navigate(R.id.action_planFragment_to_stretchingFragment)
                }else if (plans.planTitle == "Weight Training"){
                    findNavController().navigate(R.id.action_planFragment_to_WTFragment)
                }else if (plans.planTitle == "Yoga"){
                    findNavController().navigate(R.id.action_planFragment_to_yogaFragment)
                }
            }
        )
        rcv.adapter = adapter

        rcv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.data.observe(viewLifecycleOwner) { plan ->
            adapter.plans = plan
        }

        return binding.root
    }
}