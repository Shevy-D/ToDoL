package com.shevy.todol.presentation.add

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shevy.todol.utils.OnDialogCloseListener
import com.shevy.todol.R
import com.shevy.todol.data.model.database.TaskEntity
import com.shevy.todol.databinding.AddNewTaskBinding
import com.shevy.todol.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewTask : BottomSheetDialogFragment() {

    private lateinit var binding: AddNewTaskBinding
    private lateinit var mEditText: EditText
    lateinit var mSaveButton: Button
    private val viewModel by viewModel<TaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddNewTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEditText = binding.editTextANT
        mSaveButton = binding.buttonSave

        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            mEditText.setText(task)
            if (!task.isNullOrEmpty()) {
                mSaveButton.isClickable = true
                mSaveButton.isEnabled = true
                mSaveButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            }
        } else {
            mSaveButton.isClickable = false
            mSaveButton.isEnabled = false
            mSaveButton.setBackgroundColor(Color.GRAY)
        }

        val finalIsUpdate = isUpdate

        mEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    mSaveButton.isClickable = false
                    mSaveButton.isEnabled = false
                    mSaveButton.setBackgroundColor(Color.GRAY)

                    mEditText.setOnKeyListener { _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER /*|| event.action == KeyEvent.ACTION_DOWN*/) {
                            Toast.makeText(context, R.string.string_is_empty, Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                } else {
                    mSaveButton.isClickable = true
                    mSaveButton.isEnabled = true
                    mSaveButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))

                    mEditText.setOnKeyListener { _, keyCode, event ->
                        if (keyCode == KeyEvent.KEYCODE_ENTER /*|| event.action == KeyEvent.ACTION_DOWN*/) {
                            val task = mEditText.text.toString()
                            saveTask(finalIsUpdate, bundle, task)
                        }
                        true
                    }

                    mSaveButton.setOnClickListener {
                        val task = mEditText.text.toString()
                        saveTask(finalIsUpdate, bundle, task)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun saveTask(finalIsUpdate: Boolean, bundle: Bundle?, task: String) {

        viewModel.initDatabase()

        if (finalIsUpdate) {
            val taskEntry = TaskEntity(bundle!!.getInt("id"), task, 0)
            viewModel.updateTask(taskEntry)
        } else {
            val taskEntry = TaskEntity(0, task, 0)
            viewModel.insertTasks(taskEntry)
        }
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = activity
        if (activity is OnDialogCloseListener) {
            (activity as OnDialogCloseListener).onDialogClose(dialog)
        }
    }

    companion object {
        const val TAG = "AddNewTask"
        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }
}