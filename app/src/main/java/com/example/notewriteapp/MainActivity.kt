package com.example.notewriteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notewriteapp.database.RecordDatabase
import com.example.notewriteapp.repository.RecordRepository
import com.example.notewriteapp.viewmodel.RecordViewModel
import com.example.notewriteapp.viewmodel.RecordViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var recordViewModel: RecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

    }
    private fun setupViewModel(){
        val recordRepository = RecordRepository(RecordDatabase(this))
        val viewModelProviderFactory = RecordViewModelFactory(application, recordRepository)
        recordViewModel = ViewModelProvider(this, viewModelProviderFactory)[RecordViewModel::class.java]
    }
}


