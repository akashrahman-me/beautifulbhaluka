package com.akash.beautifulbhaluka.data.repository

import com.akash.beautifulbhaluka.domain.model.NewsArticle
import com.akash.beautifulbhaluka.domain.repository.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    private val _newsArticles = MutableStateFlow<List<NewsArticle>>(emptyList())
    private val currentUserId = "current_user_id"
    private val currentUserName = "আপনার নাম"

    init {
        // Initialize with mock data
        _newsArticles.value = generateMockNewsArticles()
    }

    override suspend fun getNewsArticles(): Result<List<NewsArticle>> {
        delay(500) // Simulate network delay
        return Result.success(_newsArticles.value.sortedByDescending { it.addedAt })
    }

    override suspend fun getNewsArticleById(id: String): Result<NewsArticle> {
        delay(300)
        val article = _newsArticles.value.find { it.id == id }
        return if (article != null) {
            Result.success(article)
        } else {
            Result.failure(Exception("Article not found"))
        }
    }

    override suspend fun submitNewsArticle(url: String): Result<NewsArticle> {
        delay(1000) // Simulate network delay for scraping

        // Mock article creation - in real app, this would scrape the URL
        val newArticle = NewsArticle(
            id = "news_${System.currentTimeMillis()}",
            url = url,
            title = extractTitleFromUrl(url),
            content = "This is a preview of the article content. In a real implementation, this would be scraped from the URL...",
            thumbnailUrl = "https://picsum.photos/seed/${System.currentTimeMillis()}/800/600",
            sourceUrl = url,
            addedAt = System.currentTimeMillis(),
            userId = currentUserId,
            userName = currentUserName
        )

        _newsArticles.value = listOf(newArticle) + _newsArticles.value
        return Result.success(newArticle)
    }

    override suspend fun deleteNewsArticle(id: String): Result<Unit> {
        delay(300)
        _newsArticles.value = _newsArticles.value.filter { it.id != id }
        return Result.success(Unit)
    }

    override fun observeNewsArticles(): Flow<List<NewsArticle>> {
        return _newsArticles.asStateFlow()
    }

    private fun extractTitleFromUrl(url: String): String {
        // Simple title extraction - in real app would scrape the page
        return url.substringAfter("://")
            .substringBefore("/")
            .replace("www.", "")
            .split(".")
            .firstOrNull()
            ?.replaceFirstChar { it.uppercase() } ?: "News Article"
    }

    private fun generateMockNewsArticles(): List<NewsArticle> {
        return listOf(
            NewsArticle(
                id = "news_1",
                url = "https://www.prothomalo.com/bangladesh/district/sample-article-1",
                title = "ময়মনসিংহে নতুন উন্নয়ন প্রকল্পের উদ্বোধন",
                content = "ভালুকা উপজেলায় একটি নতুন সেতু নির্মাণ প্রকল্পের উদ্বোধন করা হয়েছে। এই প্রকল্পের মাধ্যমে এলাকার যোগাযোগ ব্যবস্থার উল্লেখযোগ্য উন্নতি হবে বলে আশা করা হচ্ছে...",
                thumbnailUrl = "https://picsum.photos/seed/news1/800/600",
                sourceUrl = "https://www.prothomalo.com",
                addedAt = System.currentTimeMillis() - 3600000,
                userId = "user_1",
                userName = "রহিম উদ্দিন"
            ),
            NewsArticle(
                id = "news_2",
                url = "https://www.bdnews24.com/bangladesh/sample-article-2",
                title = "শিক্ষা খাতে নতুন উদ্যোগ",
                content = "স্থানীয় শিক্ষা প্রতিষ্ঠানগুলোতে ডিজিটাল লাইব্রেরি স্থাপনের উদ্যোগ নেওয়া হয়েছে। এর ফলে শিক্ষার্থীরা আধুনিক প্রযুক্তির সুবিধা পাবে...",
                thumbnailUrl = "https://picsum.photos/seed/news2/800/600",
                sourceUrl = "https://www.bdnews24.com",
                addedAt = System.currentTimeMillis() - 7200000,
                userId = "user_2",
                userName = "করিম আহমেদ"
            ),
            NewsArticle(
                id = "news_3",
                url = "https://www.dhakatribune.com/bangladesh/sample-article-3",
                title = "কৃষি উৎপাদনে নতুন রেকর্ড",
                content = "এ বছর ভালুকা উপজেলায় ধান উৎপাদনে রেকর্ড গড়েছেন কৃষকরা। আধুনিক চাষাবাদ পদ্ধতি ও সরকারি সহায়তার ফলে এই সাফল্য এসেছে...",
                thumbnailUrl = "https://picsum.photos/seed/news3/800/600",
                sourceUrl = "https://www.dhakatribune.com",
                addedAt = System.currentTimeMillis() - 10800000,
                userId = "user_3",
                userName = "আব্দুল কাদের"
            ),
            NewsArticle(
                id = "news_4",
                url = "https://www.newagebd.net/article/sample-article-4",
                title = "স্বাস্থ্য সেবায় উন্নতি",
                content = "উপজেলা স্বাস্থ্য কমপ্লেক্সে নতুন যন্ত্রপাতি যুক্ত করা হয়েছে। এর ফলে রোগীরা আরও ভালো চিকিৎসা সেবা পাবেন বলে আশা করা হচ্ছে...",
                thumbnailUrl = "https://picsum.photos/seed/news4/800/600",
                sourceUrl = "https://www.newagebd.net",
                addedAt = System.currentTimeMillis() - 14400000,
                userId = "user_4",
                userName = "সালমা বেগম"
            ),
            NewsArticle(
                id = "news_5",
                url = "https://www.jugantor.com/bangladesh/sample-article-5",
                title = "যুব উন্নয়ন কর্মসূচি চালু",
                content = "তরুণদের দক্ষতা বৃদ্ধির জন্য নতুন প্রশিক্ষণ কর্মসূচি শুরু হয়েছে। এতে তথ্য প্রযুক্তি, কৃষি ও ব্যবসা বিষয়ে প্রশিক্ষণ দেওয়া হবে...",
                thumbnailUrl = "https://picsum.photos/seed/news5/800/600",
                sourceUrl = "https://www.jugantor.com",
                addedAt = System.currentTimeMillis() - 18000000,
                userId = "user_5",
                userName = "মাহমুদ হাসান"
            )
        )
    }
}

