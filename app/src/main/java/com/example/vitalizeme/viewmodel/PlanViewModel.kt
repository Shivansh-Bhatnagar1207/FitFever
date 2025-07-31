package com.example.vitalizeme.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vitalizeme.repository.PlanRepository

class PlanViewModel(private val repo : PlanRepository) : ViewModel() {
    val data = repo._Plandata
}