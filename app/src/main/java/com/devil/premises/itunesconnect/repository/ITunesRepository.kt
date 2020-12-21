package com.devil.premises.itunesconnect.repository

import com.devil.premises.itunesconnect.api.RetrofitInstance
import com.devil.premises.itunesconnect.db.ResultDatabase
import com.devil.premises.itunesconnect.models.ITuneResponse
import retrofit2.Response

class ITunesRepository(
        val db: ResultDatabase
        ) {
    suspend fun searchITune(searchQuery: String): Response<ITuneResponse> {
        return RetrofitInstance.api.getTune(searchQuery)
    }
}