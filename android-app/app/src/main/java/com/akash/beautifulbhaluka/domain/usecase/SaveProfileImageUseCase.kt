package com.akash.beautifulbhaluka.domain.usecase

import android.net.Uri
import com.akash.beautifulbhaluka.domain.repository.ImageRepository
import javax.inject.Inject

class SaveProfileImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(uri: Uri): Result<String> {
        return try {
            // Business logic: validate image, compress, etc.
            imageRepository.saveProfileImage(uri)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

