package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import javax.inject.Inject

class AddWritingCommentUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(writingId: String, comment: Comment): Result<Comment> {
        return repository.addComment(writingId, comment)
    }
}

