package com.example.vitalizeme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vitalizeme.R
import com.example.vitalizeme.model.WT

class WTRepository {

    private val _wt = MutableLiveData<List<WT>>()
    val wt: LiveData<List<WT>> get() = _wt


    init {
        _wt.value = listOf(
            WT(
                R.drawable.g1,
                "Bicep Curls x 10",
                15
            ),
            WT(
                R.drawable.g2,
                "OverHead Tricep Extensions x 10",
                8
            ),
            WT(
                R.drawable.g3,
                "Weighted Squats x 10",
                12
            ),
            WT(
                R.drawable.g4,
                "Inclined Bench Press x 10",
                12
            ),
            WT(
                R.drawable.g5,
                "Lat Pulldown x 10",
                12
            ),
            WT(
                R.drawable.g6,
                "Shoulder Press x 10",
                12
            )
        )
    }
}