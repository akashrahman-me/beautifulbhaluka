package com.akash.beautifulbhaluka.di

import android.content.Context
import com.akash.beautifulbhaluka.data.local.recorder.VoiceRecorderImpl
import com.akash.beautifulbhaluka.domain.repository.VoiceRecorder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Dependency Injection Module for Voice Recording
 * Following Hilt best practices with proper scoping
 */
@Module
@InstallIn(ViewModelComponent::class)
object RecorderModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceRecorder(
        @ApplicationContext context: Context
    ): VoiceRecorder {
        return VoiceRecorderImpl(context)
    }
}

