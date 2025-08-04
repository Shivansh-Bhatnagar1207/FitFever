package com.example.vitalizeme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vitalizeme.R
import com.example.vitalizeme.model.Stretchs
import com.example.vitalizeme.repository.stretchingRepository

class StretchViewModel( private val repo : stretchingRepository) : ViewModel(){

    private val _data = MutableLiveData<Stretchs>()
    val data : LiveData<Stretchs> get() = _data

    private var currIndex = 0
    private var exc = repo.getList()

    init{
        if(exc.isNotEmpty()){
            _data.value = exc[currIndex]

        }
    }

    fun nextCard(){
        if(exc.isNotEmpty() && currIndex < exc.size - 1){
            currIndex = currIndex + 1 % exc.size
            _data.value = exc[currIndex]
        } else {
            // Send a special "end" marker
            _data.value = Stretchs(R.drawable.goodjob,"", "",  5)
        }
    }

}