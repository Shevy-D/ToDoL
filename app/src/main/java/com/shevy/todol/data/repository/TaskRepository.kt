package com.shevy.todol.data.repository

import com.shevy.todol.data.model.database.TaskEntity
import com.shevy.todol.data.room.data.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTasks(entity: TaskEntity) {
        taskDao.insert(entity)
    }

    suspend fun deleteTask(entity: TaskEntity) {
        taskDao.delete(entity)
    }

    suspend fun updateStatus(id: Int, status: Int){
        taskDao.updateStatus(id, status)
    }

    suspend fun updateTask(entity: TaskEntity) = taskDao.update(entity)

    suspend fun deleteAllTasks() {
        taskDao.deleteAll()
    }

    fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()
}