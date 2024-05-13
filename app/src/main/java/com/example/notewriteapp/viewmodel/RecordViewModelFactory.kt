package com.example.notewriteapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notewriteapp.repository.RecordRepository

class RecordViewModelFactory(val app:Application, private val recordRepository: RecordRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecordViewModel(app, recordRepository) as T
    }
}