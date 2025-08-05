package com.example.vitalizeme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vitalizeme.R
import com.example.vitalizeme.model.Plans

class PlanRepository {
    private val _Plandata = MutableLiveData<List<Plans>>()
    val Plandata : LiveData<List<Plans>>
        get() = _Plandata

    init{
        _Plandata.value = listOf<Plans>(
            Plans(R.drawable.cardio,"Cardio"),
            Plans(R.drawable.meditation,"Meditation"),
            Plans(R.drawable.strech,"Stretching"),
            Plans(R.drawable.wt,"Weight Training"),
//            Plans(R.drawable.yoga,"Yoga"),
        )
    }
}