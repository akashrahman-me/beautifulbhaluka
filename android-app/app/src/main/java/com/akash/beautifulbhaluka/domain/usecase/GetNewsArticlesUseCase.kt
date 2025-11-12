package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.NewsArticle
import com.akash.beautifulbhaluka.domain.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNewsArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Result<List<NewsArticle>> {
        return repository.getNewsArticles()
    }
}

