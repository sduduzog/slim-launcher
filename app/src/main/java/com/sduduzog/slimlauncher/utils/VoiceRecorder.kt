package com.sduduzog.slimlauncher.utils

import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Note
import java.io.IOException
import java.text.DateFormat
import java.util.*


class VoiceRecorder private constructor() {

    var state: Int = IDLE
        set(value) {
            stateLiveData.value = value
            field = value
        }
    val stateLiveData = MutableLiveData<Int>()
    private var note: Note? = null
    private var mediaRecorder: MediaRecorder? = null

    fun startRecording(fileName: String) {
        val timestamp = Date().time
        val title = "VN-$timestamp"
        val body = "Recorded at ${DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Date(timestamp))}"
        val path = "$fileName/$timestamp.3gp"
        note = Note(timestamp, body, title, timestamp, Note.TYPE_VOICE, path)
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setOutputFile(path)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = RECORDING
        } catch (e: IOException) {
            note = null
            state = ERROR
        }
    }

    fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        state = IDLE
    }

    fun save(viewModel: MainViewModel) {
        viewModel.add(note!!)
    }

    fun onStart() {
        state = IDLE
        mediaRecorder = MediaRecorder()
    }

    fun onStop() {
        Log.d("check", "on stop recording")
        mediaRecorder?.release()
        mediaRecorder = null
    }

    companion object {

        @Volatile
        private var INSTANCE: VoiceRecorder? = null

        fun getInstance(): VoiceRecorder {
            synchronized(VoiceRecorder::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = VoiceRecorder()
                }
                return INSTANCE!!
            }
        }

        const val RECORDING = 1
        const val IDLE = 2
        const val ERROR = 4
    }
}