package com.example.notewriteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notewriteapp.model.Record


@Database(entities = [Record::class], version = 1)
abstract class RecordDatabase: RoomDatabase() {
    abstract fun getRecordDao(): RecordDao

    companion object{
        @Volatile
        private var instance: RecordDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RecordDatabase::class.java,
                "record_db"
            ).build()
    }
}