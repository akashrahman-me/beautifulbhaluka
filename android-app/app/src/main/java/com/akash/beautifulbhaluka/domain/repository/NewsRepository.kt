package com.akash.beautifulbhaluka.domain.repository

import com.akash.beautifulbhaluka.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsArticles(): Result<List<NewsArticle>>
    suspend fun getNewsArticleById(id: String): Result<NewsArticle>
    suspend fun submitNewsArticle(url: String): Result<NewsArticle>
    suspend fun deleteNewsArticle(id: String): Result<Unit>
    fun observeNewsArticles(): Flow<List<NewsArticle>>
}

