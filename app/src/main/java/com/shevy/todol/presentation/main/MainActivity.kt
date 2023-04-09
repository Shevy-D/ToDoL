package com.shevy.todol.presentation.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shevy.todol.utils.OnDialogCloseListener
import com.shevy.todol.utils.RecyclerViewTouchHelper
import com.shevy.todol.adapter.ToDoAdapter
import com.shevy.todol.databinding.ActivityMainBinding
import com.shevy.todol.presentation.add.AddNewTask
import com.shevy.todol.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnDialogCloseListener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ToDoAdapter
    private val viewModel by viewModel<TaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mRecyclerView: RecyclerView = binding.recyclerViewMA
        val fab = binding.fab

        viewModel.initDatabase()
        adapter = ToDoAdapter(viewModel, this@MainActivity)
        lifecycleScope.launchWhenStarted {
            viewModel.getAllTasks().collect { listTasks ->
                adapter.setTasks(listTasks.asReversed())
            }
        }

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter

        fab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }

        val itemTouchHelper = ItemTouchHelper(RecyclerViewTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDialogClose(dialogInterface: DialogInterface?) {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllTasks().collect { listTasks ->
                adapter.setTasks(listTasks.asReversed())
            }
        }
        adapter.notifyDataSetChanged()
    }
}