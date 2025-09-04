package com.example.vitalizeme.repository

import com.example.vitalizeme.model.WorkoutDAO
import com.example.vitalizeme.model.WorkoutDB

class WorkoutRepository(private val WorkoutStorage : WorkoutDAO) {

    suspend fun addWorkout(workout : WorkoutDB){
        WorkoutStorage.insertWorkout(workout)
    }

    suspend fun updateWorkout(workout: WorkoutDB){
        WorkoutStorage.updateWorkout(workout)
    }
}