package com.example.parsearxml2.model

import javax.inject.Inject

class JobsRepository @Inject constructor() {
     val api= JobsService()

    suspend fun get():List<Job> = api.get()
}