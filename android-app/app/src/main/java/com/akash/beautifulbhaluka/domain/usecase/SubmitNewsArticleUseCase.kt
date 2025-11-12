package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.NewsArticle
import com.akash.beautifulbhaluka.domain.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubmitNewsArticleUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(url: String): Result<NewsArticle> {
        if (url.isBlank()) {
            return Result.failure(Exception("URL cannot be empty"))
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return Result.failure(Exception("Invalid URL format"))
        }

        return repository.submitNewsArticle(url)
    }
}

