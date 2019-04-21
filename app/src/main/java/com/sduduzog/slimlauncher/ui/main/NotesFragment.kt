package com.sduduzog.slimlauncher.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.NotesAdapter
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.utils.*
import kotlinx.android.synthetic.main.notes_fragment.*


class NotesFragment : BaseFragment(), OnShitDoneToNotesListener {

    override fun getFragmentView(): ViewGroup = notes_fragment

    private lateinit var viewModel: MainViewModel
    private val voiceRecorder = VoiceRecorder.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = NotesAdapter(this)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("Activity null, something here is fucked up")
        viewModel.notes.observe(this, Observer {
            it?.let { list ->
                adapter.setItems(list)
            }
        })
        notes_fragment_list.adapter = adapter
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
        itemTouchHelper.attachToRecyclerView(notes_fragment_list)
        notes_fragment_create_note.setOnClickListener {
            if (voiceRecorder.state == VoiceRecorder.Companion.State.RECORDING) {
                try {
                    voiceRecorder.stopRecording()
                    voiceRecorder.save(viewModel)
                } catch (e: RuntimeException) {
                    Log.e("check", "$e")
                }
            } else {
                Navigation.findNavController(notes_fragment)
                        .navigate(R.id.action_notesFragment_to_editNoteFragment)
            }
        }
        notes_fragment_create_note.setOnLongClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(activity!!,
                    Manifest.permission.RECORD_AUDIO)
            val isPermissionGranted = permissionCheck == PackageManager.PERMISSION_GRANTED
            if (isPermissionGranted) voiceRecorder.startRecording(context!!.filesDir.absolutePath)
            else ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    Permissions.RECORD_AUDIO)
            true
        }
        voiceRecorder.stateLiveData.observe(this, Observer {
            it?.let { state ->
                when (state) {
                    VoiceRecorder.Companion.State.RECORDING -> {
                        notes_fragment_create_note.text = getString(R.string.notes_fragment_stop_recording)
                    }
                    VoiceRecorder.Companion.State.IDLE -> {
                        notes_fragment_create_note.text = getString(R.string.notes_fragment_create_new_note)
                    }
                    VoiceRecorder.Companion.State.ERROR -> {
                    }
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        voiceRecorder.onStart()
    }

    override fun onStop() {
        super.onStop()
        voiceRecorder.onStop()
    }

    override fun onView(note: Note) {
        val bundle = Bundle()
        bundle.putLong(getString(R.string.nav_key_note), note.id)
        if (note.is_voice)
            Navigation.findNavController(notes_fragment)
                    .navigate(R.id.action_notesFragment_to_voiceNoteFragment, bundle)
        else
            Navigation.findNavController(notes_fragment)
                    .navigate(R.id.action_notesFragment_to_noteFragment, bundle)
    }

    override fun onDelete(note: Note) {
        viewModel.remove(note)
    }
}