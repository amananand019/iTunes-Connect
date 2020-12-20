package com.devil.premises.itunesconnect.repository

import com.devil.premises.itunesconnect.api.RetrofitInstance
import com.devil.premises.itunesconnect.db.ResultDatabase

class ITunesRepository(
        val db: ResultDatabase
        ) {
    suspend fun searchITune(searchQuery: String){
        RetrofitInstance.api.getTune(searchQuery)
    }
}