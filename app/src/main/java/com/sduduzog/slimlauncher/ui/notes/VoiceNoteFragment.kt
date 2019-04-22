package com.sduduzog.slimlauncher.ui.notes

import android.media.MediaPlayer
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.voice_note_fragment.*

class VoiceNoteFragment : BaseFragment() {

    override fun getFragmentView(): ViewGroup = voice_note_fragment

    private lateinit var viewModel: MainViewModel
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.voice_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("This is just dumb")
        var id = 0L
        arguments.let {
            id = if (it != null && it.containsKey(getString(R.string.nav_key_note))) {
                it.getLong(getString(R.string.nav_key_note))
            } else throw Exception("How did we get here??")
        }
        viewModel.notes.observe(this, Observer {
            it?.let { notes ->
                notes.firstOrNull { n -> n.id == id }?.let { note -> loadNote(note) }
            }
        })
        voice_note_fragment_play.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
                voice_note_fragment_play.text = getString(R.string.voice_note_fragment_play)
            } else {
                mediaPlayer?.start()
                voice_note_fragment_play.text = getString(R.string.voice_note_fragment_pause)
            }
        }
        voice_note_fragment_stop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.prepare()
            showDuration()
            voice_note_fragment_play.text = getString(R.string.voice_note_fragment_play)
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
    }

    private fun loadNote(note: Note) {
        note.title?.let { title -> if (title.isNotBlank()) voice_note_fragment_title.text = title }
        voice_note_fragment_body.text = note.body
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(note.path)
        mediaPlayer?.prepare()
        showDuration()
        voice_note_fragment_options.setOnClickListener {
            showPopupMenu(it).setOnMenuItemClickListener {

                true
            }
        }
    }

    private fun showDuration() {
        mediaPlayer?.let {
            val duration = it.duration
            voice_note_fragment_counter.text = DateUtils.formatElapsedTime(duration.toLong() / 1000)
        }
    }

    private fun showPopupMenu(view: View): PopupMenu {
        val popup = PopupMenu(context!!, view)
        popup.menuInflater.inflate(R.menu.voice_note_popup_menu, popup.menu)
        popup.show()
        return popup
    }
}