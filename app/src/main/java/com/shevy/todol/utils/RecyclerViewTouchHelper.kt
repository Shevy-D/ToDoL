package com.shevy.todol.utils

import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.shevy.todol.R
import com.shevy.todol.adapter.ToDoAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class RecyclerViewTouchHelper(private val adapter: ToDoAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.RIGHT) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(adapter.getContext())
            builder.apply {
                setTitle(R.string.delete_taks)
                setMessage(R.string.are_you_sure)
                setPositiveButton(R.string.yes) { _, _ -> adapter.deleteTask(position) }
                setNegativeButton(R.string.cancel) { _, _ -> adapter.notifyItemChanged(position) }
            }

            val dialog: AlertDialog = builder.create()
            dialog.apply {
                setCanceledOnTouchOutside(false)
                setCancelable(false)
                show()
            }
        } else {
            adapter.editTask(position)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        adapter.getContext().let { ContextCompat.getColor(it, R.color.colorPrimaryDark) }.let {
            RecyclerViewSwipeDecorator.Builder(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addSwipeLeftBackgroundColor(it)
                .addSwipeLeftActionIcon(R.drawable.ic_edit)
                .addSwipeRightBackgroundColor(Color.RED)
                .addCornerRadius(1, 8)
                .addSwipeRightActionIcon(R.drawable.ic_delete)
                .create()
                .decorate()
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }
}
