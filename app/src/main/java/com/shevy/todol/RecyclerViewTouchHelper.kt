package com.shevy.todol

import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
            builder.setTitle("Delete Task")
            builder.setMessage("Are You Sure ?")
            builder.setPositiveButton("Yes") { _, _ -> adapter.deleteTask(position) }
            builder.setNegativeButton("Cancel") { _, _ -> adapter.notifyItemChanged(position) }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            adapter.editItem(position)
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

        adapter.getContext()?.let { ContextCompat.getColor(it, R.color.colorPrimaryDark) }?.let {
            RecyclerViewSwipeDecorator.Builder(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addSwipeLeftBackgroundColor(
                    it
                )
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete)
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