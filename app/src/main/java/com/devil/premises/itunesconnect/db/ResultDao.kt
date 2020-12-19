package com.devil.premises.itunesconnect.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.devil.premises.itunesconnect.models.Result

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(result: Result): Long

    @Query("SELECT * FROM results")
    fun getAllResults(): LiveData<List<Result>>

    @Delete
    suspend fun deleteResult(result: Result)
}