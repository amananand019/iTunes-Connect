package com.devil.premises.itunesconnect.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.devil.premises.itunesconnect.models.Result

@Database(
        entities = [Result::class],
        version = 3
)
abstract class ResultDatabase: RoomDatabase() {
    abstract fun getResultDao(): ResultDao

    companion object{
        @Volatile
        private var instance: ResultDatabase?= null
        private val LOCK = Any()

//        val migrate_1_2: Migration = object : Migration(1,2)

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        ResultDatabase::class.java,
                        "result_db.db"
                ).fallbackToDestructiveMigration()
                        .build()
    }
}