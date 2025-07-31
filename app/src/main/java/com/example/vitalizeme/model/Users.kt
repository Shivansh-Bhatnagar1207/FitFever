package com.example.vitalizeme.model

import java.util.Date


data class Users(
    val name: String = "",
    val phone: Long? = null,
    val DOB: Date? = null,
    val height: String = "",
    val weight: String = "",
    val gender: String? = null,
)
