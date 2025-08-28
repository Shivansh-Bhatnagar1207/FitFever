package com.example.vitalizeme.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkoutUploader(context : Context, workParams : WorkerParameters) : Worker(context,workParams) {

    override fun doWork(): Result {
        TODO("Not yet implemented")
    }

}