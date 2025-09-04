package com.example.vitalizeme.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Workout")
data class WorkoutDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val kcalCount: Int,
    val time: Int,          // in seconds or minutes
    val workoutCount: Int,
    val steps: Int,
    val date: String,       // e.g. "2025-08-27"

)

