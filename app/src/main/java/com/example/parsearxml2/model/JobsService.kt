package com.example.parsearxml2.model

import com.example.parsearxml2.data.api.JobApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JobsService @Inject constructor() {
    val api= JobApiClient()

    suspend fun get():List<Job>{
        return withContext(Dispatchers.IO){
            val result= api.get()
            val response = api.getNewJobs(result)

            response ?: emptyList()
        }
    }
}