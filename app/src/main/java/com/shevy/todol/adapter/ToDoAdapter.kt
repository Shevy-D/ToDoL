package com.shevy.todol.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.shevy.todol.AddNewTask
import com.shevy.todol.MainActivity
import com.shevy.todol.R
import com.shevy.todol.model.ToDoModel
import com.shevy.todol.utils.DataBaseHelper


class ToDoAdapter(private val myDB: DataBaseHelper, private val activity: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    private var mList: ArrayList<ToDoModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (task, id, status) = mList!![position]
        holder.mCheckBox.text = task
        holder.mCheckBox.isChecked = toBoolean(status)
        holder.mCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myDB.updateStatus(id, 1)
            } else myDB.updateStatus(id, 0)
        }
    }

    private fun toBoolean(num: Int): Boolean {
        return num != 0
    }

    fun getContext(): Context {
        return activity
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(mList: ArrayList<ToDoModel>?) {
        this.mList = mList
        notifyDataSetChanged()
    }

    fun deleteTask(position: Int) {
        val (_, id) = mList!![position]
        myDB.deleteTask(id)
        mList!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editItem(position: Int) {
        val (task1, id) = mList!![position]
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("task", task1)
        val task = AddNewTask()
        task.arguments = bundle
        task.show(activity.supportFragmentManager, task.tag)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mCheckBox: CheckBox

        init {
            mCheckBox = itemView.findViewById(R.id.checkbox)
        }
    }
}
