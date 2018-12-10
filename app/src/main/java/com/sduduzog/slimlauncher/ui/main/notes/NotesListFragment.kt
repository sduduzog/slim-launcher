package com.sduduzog.slimlauncher.ui.main.notes


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.OnItemActionListener
import kotlinx.android.synthetic.main.notes_list_fragment.*


class NotesListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notes_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab_add_note.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openNoteFragment))
        fab_add_note.setOnLongClickListener {
            Log.d("NoteListFragment", "Long press")
            true
        }

        val adapter = NotesListAdapter(this)

        notesList.adapter = adapter

        val listener: OnItemActionListener = adapter

        val simpleItemTouchCallback = object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(0, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // Do nothing
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listener.onViewSwiped(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        itemTouchHelper.attachToRecyclerView(notesList)
        showFabAnimation(fab_add_note)
    }

    private fun showFabAnimation(targetView: View) {
        targetView.scaleX = 0f
        targetView.scaleY = 0f
        targetView.animate().scaleY(1f).scaleX(1f).duration = 100
        // Animate fab
    }
}
