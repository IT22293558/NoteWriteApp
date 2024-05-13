package com.example.notewriteapp.repository


import com.example.notewriteapp.database.RecordDatabase
import com.example.notewriteapp.model.Record

class RecordRepository(private val db:RecordDatabase) {

    suspend fun insertRecord(record: Record) = db.getRecordDao().insertRecord(record)
    suspend fun deleteRecord(record: Record) = db.getRecordDao().deleteRecord(record)
    suspend fun updateRecord(record: Record) = db.getRecordDao().updateRecord(record)

    fun getAllRecords() = db.getRecordDao().getAllRecords()
    fun searchRecord(query: String?) = db.getRecordDao().searchRecord(query)
}