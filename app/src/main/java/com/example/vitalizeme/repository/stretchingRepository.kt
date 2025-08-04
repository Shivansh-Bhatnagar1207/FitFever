package com.example.vitalizeme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vitalizeme.R
import com.example.vitalizeme.model.Stretchs

class stretchingRepository {

    fun getList(): List<Stretchs> {
        return listOf(
            Stretchs(
                R.drawable.hamstring,
                "Hamstring Stretch",
                "Stretches hamstrings and lower back.",
                0
            ),
            Stretchs(
                R.drawable.shoulder_stretch,
                "Shoulder Stretch",
                "Improves shoulder flexibility.",
                1
            ),
            Stretchs(
                R.drawable.quadriceps_stretch,
                "Quad Stretch",
                "Stretches quadriceps and hip flexors.",
                2
            ),
            Stretchs(
                R.drawable.calfstretch,
                "Calf Stretch",
                "Stretches calf muscles..",
                3),
            Stretchs(
                R.drawable.lowerbackstretch,
                "Lower Back Stretch",
                "Relieves tension in lower back.",
                4
            ),
        )
    }

}