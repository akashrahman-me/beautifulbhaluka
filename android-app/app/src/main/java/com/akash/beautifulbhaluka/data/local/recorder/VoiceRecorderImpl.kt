package com.akash.beautifulbhaluka.data.local.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.akash.beautifulbhaluka.domain.repository.VoiceRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File

/**
 * Voice Recorder Implementation using MediaRecorder
 * Following clean architecture and modern Android best practices
 */
class VoiceRecorderImpl(
    private val context: Context
) : VoiceRecorder {

    private var mediaRecorder: MediaRecorder? = null
    private var currentOutputFile: File? = null
    private var timerJob: Job? = null

    private val _isRecording = MutableStateFlow(false)
    override val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _recordingDuration = MutableStateFlow(0L)
    override val recordingDuration: StateFlow<Long> = _recordingDuration.asStateFlow()

    override suspend fun startRecording(outputFile: File): Result<Unit> {
        return try {
            // Ensure previous recorder is released
            release()

            currentOutputFile = outputFile

            // Create MediaRecorder based on Android version
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(outputFile.absolutePath)

                prepare()
                start()
            }

            _isRecording.value = true
            _recordingDuration.value = 0L

            // Start duration timer
            startDurationTimer()

            Result.success(Unit)
        } catch (e: Exception) {
            release()
            Result.failure(e)
        }
    }

    override suspend fun stopRecording(): Result<String> {
        return try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null

            timerJob?.cancel()
            timerJob = null

            _isRecording.value = false

            val filePath = currentOutputFile?.absolutePath
                ?: return Result.failure(Exception("No recording file found"))

            Result.success(filePath)
        } catch (e: Exception) {
            release()
            Result.failure(e)
        }
    }

    override suspend fun cancelRecording(): Result<Unit> {
        return try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null

            timerJob?.cancel()
            timerJob = null

            _isRecording.value = false
            _recordingDuration.value = 0L

            // Delete the temp file
            currentOutputFile?.delete()
            currentOutputFile = null

            Result.success(Unit)
        } catch (e: Exception) {
            release()
            Result.failure(e)
        }
    }

    override fun release() {
        try {
            mediaRecorder?.release()
            mediaRecorder = null

            timerJob?.cancel()
            timerJob = null

            _isRecording.value = false
            _recordingDuration.value = 0L
        } catch (_: Exception) {
            // Ignore release errors
        }
    }

    private fun startDurationTimer() {
        timerJob?.cancel()
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            val startTime = System.currentTimeMillis()
            while (isActive && _isRecording.value) {
                _recordingDuration.value = System.currentTimeMillis() - startTime
                delay(100) // Update every 100ms
            }
        }
    }
}

