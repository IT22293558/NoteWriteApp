package com.example.notewriteapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "records")
@Parcelize
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val recordTitle: String,
    val recordDesc: String
):Parcelable
