package com.shevy.todol.data.room.data

import androidx.room.*
import com.shevy.todol.data.model.database.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

    @Update
    suspend fun update(taskEntity: TaskEntity)

    @Query("UPDATE task_database SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: Int)

    @Query("DELETE FROM task_database")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_database")
    fun getAllTasks(): Flow<List<TaskEntity>>
}