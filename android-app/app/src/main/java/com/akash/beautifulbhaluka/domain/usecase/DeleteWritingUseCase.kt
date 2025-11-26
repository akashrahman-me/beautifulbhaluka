package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class DeleteWritingUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(writingId: String): Result<Unit> {
        return repository.deleteWriting(writingId)
    }
}

