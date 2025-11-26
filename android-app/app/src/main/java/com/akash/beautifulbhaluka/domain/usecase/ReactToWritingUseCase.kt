package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class ReactToWritingUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(writingId: String, reaction: Reaction): Result<Unit> {
        return repository.reactToWriting(writingId, reaction).map { }
    }
}

