package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class GetWritingByIdUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(id: String): Result<Writing> {
        return repository.getWritingById(id)
    }
}

