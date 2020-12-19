package com.devil.premises.itunesconnect

data class ITuneResponse(
    val resultCount: Int,
    val results: List<Result>
)