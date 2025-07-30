package com.example.vitalizeme.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vitalizeme.repository.BannerRepository

class HomeViewModel (private val repository: BannerRepository): ViewModel() {
    val data = repository._banner
}