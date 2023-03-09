package com.example.parsearxml2.viewmodel

import android.util.MutableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parsearxml2.model.Job
import com.example.parsearxml2.model.JobsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//:ViewModel()
@HiltViewModel
 class JobsViewModel @Inject constructor() :ViewModel() {
    val repository= JobsRepository()

    val progressLiveData= MutableLiveData<Boolean>()
    val jobsLiveData = MutableLiveData<List<Job>>()

    fun onViewCreated(){
        viewModelScope.launch {
            progressLiveData.postValue(true)
            val jobs =repository.get()
            jobsLiveData.postValue(jobs)
            progressLiveData.postValue(false)
        }

    }
}