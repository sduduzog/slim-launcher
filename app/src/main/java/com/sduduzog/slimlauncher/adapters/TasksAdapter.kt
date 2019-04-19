package com.sduduzog.slimlauncher.adapters

import android.content.Context
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Task

class TasksAdapter(private val context: Context, private val viewModel: MainViewModel) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        if (task.isCompleted) {
            holder.checkBox.isChecked = true
            val text = context.getString(R.string.tasks_fragment_list_item_complete, task.body)
            val styledText: Spanned = HtmlCompat.fromHtml(text, FROM_HTML_MODE_LEGACY)
            holder.checkBox.text = styledText
        } else {
            holder.checkBox.isChecked = false
            holder.checkBox.text = task.body
        }
        holder.checkBox.setOnClickListener {
            task.isCompleted = !task.isCompleted
            viewModel.update(task)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setItems(list: List<Task>) {
        this.tasks = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.tasks_fragment_list_item)
        override fun toString(): String {
            return super.toString() + " '${checkBox.text}'"
        }
    }
}