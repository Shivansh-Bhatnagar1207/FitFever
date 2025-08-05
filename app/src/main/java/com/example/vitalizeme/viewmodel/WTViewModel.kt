package com.example.vitalizeme.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vitalizeme.repository.WTRepository

class WTViewModel(repo : WTRepository): ViewModel() {

    val data = repo.wt


}