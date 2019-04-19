package com.sduduzog.slimlauncher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.model.Note

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notes: List<Note> = listOf()

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setItems(list: List<Note>) {
        this.notes = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.notes_fragment_list_item_title)
        val itemSnippet: TextView = itemView.findViewById(R.id.notes_fragment_list_item_snippet)

        override fun toString(): String {
            return super.toString() + " '${itemTitle.text}'"
        }
    }
}