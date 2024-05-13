package com.example.notewriteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notewriteapp.model.Record
import com.example.notewriteapp.repository.RecordRepository
import kotlinx.coroutines.launch

class RecordViewModel(app:Application, private val recordRepository: RecordRepository): AndroidViewModel(app) {

    fun addRecord(record: Record) =
        viewModelScope.launch {
            recordRepository.insertRecord(record)
        }

    fun deleteRecord(record: Record) =
        viewModelScope.launch {
            recordRepository.deleteRecord(record)
        }

    fun updateRecord(record: Record) =
        viewModelScope.launch {
            recordRepository.updateRecord(record)
        }

    fun getAllRecords() = recordRepository.getAllRecords()

    fun searchRecord(query:String?) =
        recordRepository.searchRecord(query)
}