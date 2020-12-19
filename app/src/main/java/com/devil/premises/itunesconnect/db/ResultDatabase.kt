package com.devil.premises.itunesconnect.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devil.premises.itunesconnect.models.Result

@Database(
        entities = [Result::class],
        version = 1
)
abstract class ResultDatabase: RoomDatabase() {
    abstract fun getResultDao(): ResultDao

    companion object{
        @Volatile
        private var instance: ResultDatabase?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        ResultDatabase::class.java,
                        "result_db.db"
                ).build()
    }
}