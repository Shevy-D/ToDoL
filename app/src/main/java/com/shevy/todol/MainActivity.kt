package com.shevy.todol

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shevy.todol.utils.DataBaseHelper

class MainActivity : AppCompatActivity() {

    private var myDB: DataBaseHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mRecyclerview: RecyclerView = findViewById(R.id.recyclerViewMA)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        myDB = DataBaseHelper(this@MainActivity)

        fab.setOnClickListener {
            Toast.makeText(this, "You clicked me", Toast.LENGTH_SHORT).show()
            //AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }
    }
}