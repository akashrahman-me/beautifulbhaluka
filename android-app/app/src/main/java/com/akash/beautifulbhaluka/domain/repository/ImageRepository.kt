package com.akash.beautifulbhaluka.domain.repository

import android.net.Uri

interface ImageRepository {
    suspend fun saveProfileImage(uri: Uri): Result<String>
    suspend fun getProfileImagePath(profileId: String): Result<Uri?>
    suspend fun deleteProfileImage(profileId: String): Result<Unit>
}

