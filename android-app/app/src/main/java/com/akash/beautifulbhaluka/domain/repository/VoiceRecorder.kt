package com.akash.beautifulbhaluka.domain.repository

import kotlinx.coroutines.flow.StateFlow
import java.io.File

/**
 * Voice Recorder Interface
 * Following clean architecture principles
 */
interface VoiceRecorder {
    /**
     * Current recording state
     */
    val isRecording: StateFlow<Boolean>

    /**
     * Recording duration in milliseconds
     */
    val recordingDuration: StateFlow<Long>

    /**
     * Start voice recording
     * @return Result containing the output file or error
     */
    suspend fun startRecording(outputFile: File): Result<Unit>

    /**
     * Stop recording and save the file
     * @return Result containing the saved file path or error
     */
    suspend fun stopRecording(): Result<String>

    /**
     * Cancel recording and delete the temp file
     */
    suspend fun cancelRecording(): Result<Unit>

    /**
     * Release resources
     */
    fun release()
}

