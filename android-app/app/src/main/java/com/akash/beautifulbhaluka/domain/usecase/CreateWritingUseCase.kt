package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class CreateWritingUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(writing: Writing): Result<Writing> {
        return repository.createWriting(writing)
    }
}

