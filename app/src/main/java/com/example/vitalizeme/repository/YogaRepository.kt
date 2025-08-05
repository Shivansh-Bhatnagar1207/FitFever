package com.example.vitalizeme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vitalizeme.model.Yoga

class YogaRepository {

    val _yoga = MutableLiveData<List<Yoga>>()
    val yoga : LiveData<List<Yoga>> get() = _yoga

    init{
        _yoga.value = listOf(

        )
    }


}