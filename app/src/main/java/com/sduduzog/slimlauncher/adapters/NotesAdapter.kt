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

    private var notes: List<Note> = listOf()

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
        holder.itemView.setOnClickListener { listener.onViewNote(note) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setItems(list: List<Note>) {
        this.notes = list
        notifyDataSetChanged()
    }

    override fun onViewIdle() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewSwiped(position: Int) {
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.notes_fragment_list_item_title)
        val itemSnippet: TextView = itemView.findViewById(R.id.notes_fragment_list_item_snippet)

        override fun toString(): String {
            return super.toString() + " '${itemTitle.text}'"
        }
    }
}