package com.example.vitalizeme.view.main

import android.content.Intent
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
import com.example.vitalizeme.view.main.plans.PlansActivity
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
        val intent = Intent(requireContext(), PlansActivity::class.java)

        adapter = PlansAdapter(
            emptyList(),
            { plans ->
                if (plans.planTitle == "Cardio") {
                   startActivity(intent.putExtra("plan_type","Cardio"))
                }else if (plans.planTitle == "Meditation"){
                    startActivity(intent.putExtra("plan_type","mediation"))
                }else if (plans.planTitle == "Stretching"){
                    startActivity(intent.putExtra("plan_type","stretching"))
                }else if (plans.planTitle == "Weight Training"){
                    startActivity(intent.putExtra("plan_type","wt"))
                }else if (plans.planTitle == "Yoga"){
                    startActivity(intent.putExtra("plan_type","yoga"))
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