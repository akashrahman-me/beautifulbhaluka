package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class GetWritingCommentsUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(writingId: String): Result<List<Comment>> {
        return repository.getComments(writingId)
    }
}

