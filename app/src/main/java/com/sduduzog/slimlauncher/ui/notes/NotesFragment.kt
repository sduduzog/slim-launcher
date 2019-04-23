package com.sduduzog.slimlauncher.ui.notes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.format.DateUtils
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
import java.io.File


class NotesFragment : BaseFragment(), OnShitDoneToNotesListener {

    override fun getFragmentView(): ViewGroup = notes_fragment

    private lateinit var viewModel: MainViewModel
    private val voiceRecorder = VoiceRecorder.getInstance()
    private val customHandler = Handler()
    private var timeInMilliseconds = 0L
    private var startTime = 0L
    private var updateTime = 0L
    private val updateTimerThread = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis()
            updateTime = timeInMilliseconds - startTime
            val time = DateUtils.formatElapsedTime(updateTime / 1000)
            notes_fragment_create_note.text = getString(R.string.notes_fragment_stop)
            notes_fragment_counter.text = getString(R.string.notes_fragment_counter, time)
            customHandler.postDelayed(this, 100)
        }
    }

    private var isDeletePending = false
    private var noteToDelete: Note? = null

    private val deleteNoteThread = Runnable {
        if (isDeletePending) {
            noteToDelete?.let {
                deleteNote(it)
            }
        }
        notes_fragment_create_note.text = getString(R.string.notes_fragment_create_new_note)
        isDeletePending = false
    }

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
            when {
                isDeletePending -> {
                    isDeletePending = false
                    adapter.notifyDataSetChanged()
                    notes_fragment_create_note.text = getString(R.string.notes_fragment_create_new_note)
                }
                voiceRecorder.state == VoiceRecorder.RECORDING -> try {
                    voiceRecorder.stopRecording()
                    voiceRecorder.save(viewModel)
                } catch (e: RuntimeException) {
                    // I pray this doesn't happen
                }
                else -> Navigation.findNavController(notes_fragment)
                        .navigate(R.id.action_notesFragment_to_editNoteFragment)
            }
        }
        notes_fragment_create_note.setOnLongClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(activity!!,
                    Manifest.permission.RECORD_AUDIO)
            val isPermissionGranted = permissionCheck == PackageManager.PERMISSION_GRANTED
            val file = File(context!!.filesDir, getString(R.string.audio_file_path))
            val dir = file.canonicalPath
            if (isPermissionGranted) voiceRecorder.startRecording(dir)
            else ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    Permissions.RECORD_AUDIO)
            true
        }
        voiceRecorder.stateLiveData.observe(this, Observer {
            it?.let { state ->
                when (state) {
                    VoiceRecorder.RECORDING -> startTimer()
                    VoiceRecorder.IDLE -> {
                        notes_fragment_create_note.text = getString(R.string.notes_fragment_create_new_note)
                        stopTimer()
                    }
                    VoiceRecorder.ERROR -> stopTimer()
                }
            }
        })
    }

    private fun startTimer() {
        startTime = SystemClock.uptimeMillis()
        customHandler.postDelayed(updateTimerThread, 0)
        notes_fragment_counter.visibility = View.VISIBLE
    }

    private fun stopTimer() {
        customHandler.removeCallbacks(updateTimerThread)
        notes_fragment_counter.visibility = View.GONE
    }

    private fun deleteNote(note: Note) {
        if (note.type == Note.TYPE_VOICE) context?.deleteFile(note.filename)
        viewModel.remove(note)
    }

    override fun onStart() {
        super.onStart()
        voiceRecorder.onStart()
    }

    override fun onStop() {
        super.onStop()
        voiceRecorder.onStop()
        customHandler.removeCallbacks(updateTimerThread)
    }

    override fun onView(note: Note) {
        if (voiceRecorder.state == VoiceRecorder.RECORDING) return
        val bundle = Bundle()
        bundle.putLong(getString(R.string.nav_key_note), note.id)
        if (note.type == Note.TYPE_TEXT)
            Navigation.findNavController(notes_fragment)
                    .navigate(R.id.action_notesFragment_to_noteFragment, bundle)
        else
            Navigation.findNavController(notes_fragment)
                    .navigate(R.id.action_notesFragment_to_voiceNoteFragment, bundle)
    }

    override fun onDelete(note: Note) {
        notes_fragment_create_note.text = getString(R.string.notes_fragment_undo)
        if (isDeletePending) {
            customHandler.removeCallbacks(deleteNoteThread)
            noteToDelete?.let {
                deleteNote(it)
            }
        }

        noteToDelete = note.copy()
        isDeletePending = true
        customHandler.postDelayed(deleteNoteThread, 2000)
    }
}