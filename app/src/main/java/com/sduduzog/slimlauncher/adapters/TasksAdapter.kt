package com.sduduzog.slimlauncher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.model.Task

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.tasks_fragment_list_item)
        override fun toString(): String {
            return super.toString() + " '${checkBox.text}'"
        }
    }
}