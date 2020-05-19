package com.sandor.tasktimer

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_list_items.*
import java.lang.IllegalStateException

class TaskViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer

private const val TAG = "CursorRecyclerViewAdapt"

class CursorRecyclerViewAdapter(private var cursor: Cursor?):
        RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG, "onCreateViewHolder: starts")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_items,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: starts")
        val cursor = cursor
        if(cursor == null || cursor.count == 0) {
            Log.d(TAG, "onBindViewHolder: providing instructions")
            holder.tli_name.setText(R.string.instruction_heading)
            holder.tli_description.text = "holder.tli_description.setText(\"Use the add button (+) in the toolbar above to create new tasks.\" +\n" +
                    "        \"\\n\\nTasks with lower sort orders will be placed higher up the list.\" +\n" +
                    "        \"Tasks with the same sort order will be sorted alphabetically.\" +\n" +
                    "        \"\\n\\nTapping a task will start the timer for that task (and will stop the timer for any previous task that was being timed).\" +\n" +
                    "        \"\\n\\nEach task has Edit and Delete buttons if you want to change the details or remove the task.\")"
            holder.tli_edit.visibility = View.GONE
            holder.tli_delete.visibility = View.GONE
        } else {
            if(!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position $position")
            }

            val task = Task(
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_NAME)),
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(TasksContract.Columns.TASK_SORT_ORDER)))

            task.id = cursor.getLong(cursor.getColumnIndex(TasksContract.Columns.ID))
            holder.tli_name.text = task.name
            holder.tli_description.text = task.description
            holder.tli_edit.visibility = View.VISIBLE
            holder.tli_delete.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}