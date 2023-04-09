package com.shevy.todol.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shevy.todol.data.model.database.TaskEntity
import com.shevy.todol.databinding.TaskLayoutBinding
import com.shevy.todol.presentation.add.AddNewTask
import com.shevy.todol.presentation.main.MainActivity
import com.shevy.todol.viewmodel.TaskViewModel

class ToDoAdapter(private val viewModel: TaskViewModel, private val activity: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    class MyViewHolder(binding: TaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val mCheckBox = binding.checkbox
    }

    private val tasks = mutableListOf<TaskEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(newTask: List<TaskEntity>) {
        tasks.apply {
            clear()
            addAll(newTask)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = tasks[position]
        holder.mCheckBox.text = task.task
        holder.mCheckBox.isChecked = toBoolean(task.status)
        holder.mCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updateStatus(task.id, 1)
            } else viewModel.updateStatus(task.id, 0)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    private fun toBoolean(num: Int): Boolean {
        return num != 0
    }

    fun getContext(): Context {
        return activity
    }

    fun deleteTask(position: Int) {
        viewModel.deleteTask(tasks[position])
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editTask(position: Int) {
        val taskOld = tasks[position].task
        val idOld = tasks[position].id
        val bundle = Bundle()
        bundle.putInt("id", idOld)
        bundle.putString("task", taskOld)
        val task = AddNewTask()
        task.arguments = bundle
        task.show(activity.supportFragmentManager, task.tag)
    }
}
