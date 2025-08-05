package com.example.vitalizeme.model

data class WT(
    val img: Int,
    val title: String,
    val calPerSet: Int,
    var totalCal: Int = 0,
    var timeSpent: Int = 0,
    var set: Int = 0
)