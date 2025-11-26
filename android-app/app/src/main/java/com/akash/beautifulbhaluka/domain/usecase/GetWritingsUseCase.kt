package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.model.WritingCategory
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class GetWritingsUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(category: WritingCategory = WritingCategory.ALL): Result<List<Writing>> {
        return repository.getWritings(category)
    }
}

