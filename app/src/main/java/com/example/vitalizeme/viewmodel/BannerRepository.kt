package com.example.vitalizeme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vitalizeme.model.Banner

class BannerRepository {
    private val Bannerdata = MutableLiveData<List<Banner>>()
    val _banner : LiveData<List<Banner>>
        get() = Bannerdata

    init{
        Bannerdata.value = listOf<Banner>(
            Banner("Small Challenges, Big Results!!"),
            Banner("Sweat is your body's way of showing progress"),
            Banner("Results happen over time, not overnight")
        )
    }
}