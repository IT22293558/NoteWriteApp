package com.example.notewriteapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notewriteapp.model.Record

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Record)
    @Update
    suspend fun updateRecord(record: Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("SELECT * FROM RECORDS ORDER BY id DESC")
    fun getAllRecords(): LiveData<List<Record>>

    @Query("SELECT * FROM RECORDS WHERE recordTitle LIKE :query OR recordDesc LIKE :query")
    fun searchRecord(query: String?): LiveData<List<Record>>
}