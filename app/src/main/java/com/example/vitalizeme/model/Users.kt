package com.example.vitalizeme.model


data class Users(
    val name: String = "",
    val phone: String? = null,
    val DOB: String? = null,
    val height: String = "",
    val weight: String = "",
    val gender: String? = null,
)
