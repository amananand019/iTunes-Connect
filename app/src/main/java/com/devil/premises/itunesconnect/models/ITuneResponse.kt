package com.devil.premises.itunesconnect.models

data class ITuneResponse(
    val resultCount: Int,
    val results: List<Result>
)