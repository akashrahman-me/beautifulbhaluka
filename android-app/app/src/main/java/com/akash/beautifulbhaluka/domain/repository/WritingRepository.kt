package com.akash.beautifulbhaluka.domain.repository

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.model.WritingCategory

interface WritingRepository {
    suspend fun getWritings(category: WritingCategory = WritingCategory.ALL): Result<List<Writing>>
    suspend fun getWritingById(id: String): Result<Writing>
    suspend fun getWritingsByAuthor(authorId: String): Result<List<Writing>>
    suspend fun createWriting(writing: Writing): Result<Writing>
    suspend fun updateWriting(writing: Writing): Result<Writing>
    suspend fun deleteWriting(id: String): Result<Unit>
    suspend fun toggleLike(writingId: String): Result<Writing>
    suspend fun reactToWriting(writingId: String, reaction: Reaction): Result<Writing>
    suspend fun getComments(writingId: String): Result<List<Comment>>
    suspend fun addComment(writingId: String, comment: Comment): Result<Comment>
    suspend fun deleteComment(writingId: String, commentId: String): Result<Unit>
    suspend fun toggleCommentLike(writingId: String, commentId: String): Result<Comment>
}

