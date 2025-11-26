package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class GetWritingsByAuthorUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(authorId: String): Result<List<Writing>> {
        return repository.getWritingsByAuthor(authorId)
    }
}

