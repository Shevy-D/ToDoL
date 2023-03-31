package com.shevy.todol

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shevy.todol.model.ToDoModel
import com.shevy.todol.utils.DataBaseHelper


class AddNewTask : BottomSheetDialogFragment() {
    //widgets
    private lateinit var mEditText: EditText
    lateinit var mSaveButton: Button
    lateinit var myDb: DataBaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEditText = view.findViewById(R.id.editTextANT)
        mSaveButton = view.findViewById(R.id.button_save)
        myDb = DataBaseHelper(activity)
        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            mEditText.setText(task)
            if (!task.isNullOrEmpty()) {
                mSaveButton.isEnabled = false
            }
        }

        mEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    mSaveButton.isEnabled = false
                    mSaveButton.setBackgroundColor(Color.GRAY)
                } else {
                    mSaveButton.isEnabled = true
                    mSaveButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        val finalIsUpdate = isUpdate

        mEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || event.action == KeyEvent.ACTION_DOWN) {
                saveTask(finalIsUpdate, bundle)
            }
            true
        }

        mSaveButton.setOnClickListener {
            saveTask(finalIsUpdate, bundle)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = activity
        if (activity is OnDialogCloseListener) {
            (activity as OnDialogCloseListener).onDialogClose(dialog)
        }
    }

    private fun saveTask(finalIsUpdate: Boolean, bundle: Bundle?) {
        val text = mEditText.text.toString()
        if (finalIsUpdate) {
            myDb.updateTask(bundle!!.getInt("id"), text)
        } else {
            val item = ToDoModel()
            item.task = text
            item.status = 0
            myDb.insertTask(item)
        }
        dismiss()
    }

    companion object {
        const val TAG = "AddNewTask"
        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }
}