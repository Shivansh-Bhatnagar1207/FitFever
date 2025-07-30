package com.example.vitalizeme.viewmodel

import androidx.lifecycle.ViewModel

class HomeViewModel (private val repository: BannerRepository): ViewModel() {
    val data = repository._banner
}