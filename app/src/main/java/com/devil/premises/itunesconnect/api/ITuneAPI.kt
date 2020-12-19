package com.devil.premises.itunesconnect.api

import com.devil.premises.itunesconnect.ITuneResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITuneAPI {
    @GET("search")
    suspend fun getTune(
        @Query("term")
        searchTerm: String,
        @Query("country")
        countryCode: String = "IN",
        @Query("sort")
        sortType: String = "recent"
    ):Response<ITuneResponse>
}