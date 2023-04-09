package com.shevy.todol.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shevy.todol.data.model.database.TaskEntity
import com.shevy.todol.data.repository.TaskRepository
import com.shevy.todol.data.room.database.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: TaskRepository
    private val context = application

    fun initDatabase() {
        val daoTask = TaskDatabase.getInstance(context).getTaskDao()
        repository = TaskRepository(daoTask)
    }

    fun getAllTasks(): Flow<List<TaskEntity>> {
        return repository.getAllTasks()
    }

    fun insertTasks(entity: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTasks(entity = entity)
        }
    }

    fun updateStatus(id: Int, status: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStatus(id, status)
        }
    }

    fun updateTask(entity: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(entity = entity)
        }
    }

    fun deleteTask(entity: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(entity = entity)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }
}