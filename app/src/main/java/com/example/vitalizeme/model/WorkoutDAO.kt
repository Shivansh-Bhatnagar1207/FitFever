package com.example.vitalizeme.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface WorkoutDAO {

    @Insert
    suspend fun insertWorkout(workout: WorkoutDB)

    @Update
    suspend fun updateWorkout(workout: WorkoutDB)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutDB)

    @Query("SELECT * FROM Workout WHERE date = :date LIMIT 1")
    suspend fun getWorkoutByDate(date: String): WorkoutDB?

    @Query("SELECT * FROM Workout")
    suspend fun getAllWorkouts(): List<WorkoutDB>

}
