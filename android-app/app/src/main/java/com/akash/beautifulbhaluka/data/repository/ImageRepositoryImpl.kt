package com.akash.beautifulbhaluka.data.repository

import android.content.Context
import android.net.Uri
import com.akash.beautifulbhaluka.domain.repository.ImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageRepository {

    private val imageDir = File(context.filesDir, "profile_images").apply {
        if (!exists()) mkdirs()
    }

    override suspend fun saveProfileImage(uri: Uri): Result<String> = withContext(Dispatchers.IO) {
        try {
            val fileName = "profile_${UUID.randomUUID()}.jpg"
            val destinationFile = File(imageDir, fileName)

            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(destinationFile).use { output ->
                    input.copyTo(output)
                }
            }

            Result.success(destinationFile.absolutePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfileImagePath(profileId: String): Result<Uri?> =
        withContext(Dispatchers.IO) {
            try {
                val file = File(imageDir, "profile_$profileId.jpg")
                if (file.exists()) {
                    Result.success(Uri.fromFile(file))
                } else {
                    Result.success(null)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun deleteProfileImage(profileId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val file = File(imageDir, "profile_$profileId.jpg")
                if (file.exists()) {
                    file.delete()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}

