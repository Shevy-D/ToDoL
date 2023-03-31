package com.shevy.todol

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shevy.todol.adapter.ToDoAdapter
import com.shevy.todol.model.ToDoModel
import com.shevy.todol.utils.DataBaseHelper


class MainActivity : AppCompatActivity(), OnDialogCloseListener {

    lateinit var mRecyclerview: RecyclerView
    private lateinit var myDB: DataBaseHelper
    private lateinit var mList: ArrayList<ToDoModel>
    lateinit var adapter: ToDoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mRecyclerview: RecyclerView = findViewById(R.id.recyclerViewMA)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        myDB = DataBaseHelper(this@MainActivity)
        mList = ArrayList()
        adapter = ToDoAdapter(myDB, this@MainActivity)

        mRecyclerview.setHasFixedSize(true)
        mRecyclerview.layoutManager = LinearLayoutManager(this)
        mRecyclerview.adapter = adapter

        mList = myDB.allTasks
        mList.reverse()
        adapter.setTasks(mList)

        fab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }

        val itemTouchHelper = ItemTouchHelper(RecyclerViewTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(mRecyclerview)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDialogClose(dialogInterface: DialogInterface?) {
        mList = myDB.allTasks
        mList.reverse()
        adapter.setTasks(mList)
        adapter.notifyDataSetChanged()
    }
}