package com.shevy.todol.data.model.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_database")
class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val task: String,
    val status: Int
) : Parcelable