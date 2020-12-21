package com.devil.premises.itunesconnect.repository

import com.devil.premises.itunesconnect.api.RetrofitInstance
import com.devil.premises.itunesconnect.db.ResultDatabase
import com.devil.premises.itunesconnect.models.ITuneResponse
import com.devil.premises.itunesconnect.models.Result
import retrofit2.Response

class ITunesRepository(
        val db: ResultDatabase
        ) {
    suspend fun searchITune(searchQuery: String): Response<ITuneResponse> {
        return RetrofitInstance.api.getTune(searchQuery)
    }

    suspend fun upsert(result: Result) = db.getResultDao().upsert(result)

    fun getSavedITunes() = db.getResultDao().getAllResults()

    suspend fun deleteResult(result: Result) = db.getResultDao().deleteResult(result)
}