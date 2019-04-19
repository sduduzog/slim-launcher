package com.sduduzog.slimlauncher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.utils.OnItemActionListener
import com.sduduzog.slimlauncher.utils.OnShitDoneToNotesListener

class NotesAdapter(private val listener: OnShitDoneToNotesListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() , OnItemActionListener{

    private val notes: MutableList<Note> = mutableListOf()
    private var indexOfRemovedNote = 0

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        if (note.title.isNullOrEmpty()){
            holder.itemTitle.visibility = View.GONE
        } else {
            holder.itemTitle.text = note.title
            holder.itemTitle.visibility = View.VISIBLE
        }
        holder.itemSnippet.text = note.body
        holder.itemView.setOnClickListener { listener.onView(note) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setItems(list: List<Note>) {
        val size = notes.size
        notes.clear()
        notes.addAll(list)
        if (size > list.size) {
            notifyItemRemoved(indexOfRemovedNote)
        } else notifyDataSetChanged()
    }

    override fun onViewIdle() {
        // do nothing
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int): Boolean {
        // do nothing
        return false
    }

    override fun onViewSwiped(position: Int) {
        indexOfRemovedNote = position
        if (position < notes.size) {
            val note = notes[position]
            listener.onDelete(note)
        } else
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