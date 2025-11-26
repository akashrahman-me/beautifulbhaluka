package com.akash.beautifulbhaluka.data.repository

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.model.WritingCategory
import com.akash.beautifulbhaluka.domain.repository.WritingRepository
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WritingRepositoryImpl @Inject constructor() : WritingRepository {

    private val writings = mutableListOf<Writing>()
    private val commentsMap = mutableMapOf<String, MutableList<Comment>>()

    init {
        initializeMockData()
    }

    override suspend fun getWritings(category: WritingCategory): Result<List<Writing>> {
        return try {
            delay(500)
            val filtered = if (category == WritingCategory.ALL) {
                writings
            } else {
                writings.filter { it.category == category }
            }
            Result.success(filtered.sortedByDescending { it.createdAt })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWritingById(id: String): Result<Writing> {
        return try {
            delay(300)
            val writing = writings.find { it.id == id }
            if (writing != null) {
                Result.success(writing)
            } else {
                Result.failure(Exception("Writing not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWritingsByAuthor(authorId: String): Result<List<Writing>> {
        return try {
            delay(400)
            val filtered = writings.filter { it.authorId == authorId }
            Result.success(filtered.sortedByDescending { it.createdAt })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createWriting(writing: Writing): Result<Writing> {
        return try {
            delay(300)
            val newWriting = writing.copy(id = UUID.randomUUID().toString())
            writings.add(newWriting)
            commentsMap[newWriting.id] = mutableListOf()
            Result.success(newWriting)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateWriting(writing: Writing): Result<Writing> {
        return try {
            delay(300)
            val index = writings.indexOfFirst { it.id == writing.id }
            if (index != -1) {
                writings[index] = writing
                Result.success(writing)
            } else {
                Result.failure(Exception("Writing not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteWriting(id: String): Result<Unit> {
        return try {
            delay(300)
            writings.removeIf { it.id == id }
            commentsMap.remove(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleLike(writingId: String): Result<Writing> {
        return try {
            delay(200)
            val index = writings.indexOfFirst { it.id == writingId }
            if (index != -1) {
                val writing = writings[index]
                val updated = writing.copy(
                    isLiked = !writing.isLiked,
                    likes = if (writing.isLiked) writing.likes - 1 else writing.likes + 1,
                    userReaction = if (writing.isLiked) null else Reaction.LIKE
                )
                writings[index] = updated
                Result.success(updated)
            } else {
                Result.failure(Exception("Writing not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun reactToWriting(writingId: String, reaction: Reaction): Result<Writing> {
        return try {
            delay(200)
            val index = writings.indexOfFirst { it.id == writingId }
            if (index != -1) {
                val writing = writings[index]
                val likeDiff = when {
                    writing.userReaction == null -> 1
                    writing.userReaction == reaction -> -1
                    else -> 0
                }
                val updated = writing.copy(
                    userReaction = if (writing.userReaction == reaction) null else reaction,
                    likes = writing.likes + likeDiff,
                    isLiked = !(writing.userReaction == reaction)
                )
                writings[index] = updated
                Result.success(updated)
            } else {
                Result.failure(Exception("Writing not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getComments(writingId: String): Result<List<Comment>> {
        return try {
            delay(300)
            val comments = commentsMap[writingId] ?: emptyList()
            Result.success(comments.sortedByDescending { it.createdAt })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addComment(writingId: String, comment: Comment): Result<Comment> {
        return try {
            delay(300)
            val commentsList = commentsMap.getOrPut(writingId) { mutableListOf() }
            val newComment = comment.copy(id = UUID.randomUUID().toString())
            commentsList.add(newComment)

            val writingIndex = writings.indexOfFirst { it.id == writingId }
            if (writingIndex != -1) {
                val writing = writings[writingIndex]
                writings[writingIndex] = writing.copy(comments = writing.comments + 1)
            }

            Result.success(newComment)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteComment(writingId: String, commentId: String): Result<Unit> {
        return try {
            delay(200)
            val commentsList = commentsMap[writingId]
            commentsList?.removeIf { it.id == commentId }

            val writingIndex = writings.indexOfFirst { it.id == writingId }
            if (writingIndex != -1) {
                val writing = writings[writingIndex]
                writings[writingIndex] = writing.copy(comments = maxOf(0, writing.comments - 1))
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleCommentLike(writingId: String, commentId: String): Result<Comment> {
        return try {
            delay(200)
            val commentsList = commentsMap[writingId]
            val index = commentsList?.indexOfFirst { it.id == commentId } ?: -1
            if (index != -1 && commentsList != null) {
                val comment = commentsList[index]
                val updated = comment.copy(
                    isLiked = !comment.isLiked,
                    likes = if (comment.isLiked) comment.likes - 1 else comment.likes + 1
                )
                commentsList[index] = updated
                Result.success(updated)
            } else {
                Result.failure(Exception("Comment not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun initializeMockData() {
        writings.addAll(
            listOf(
                Writing(
                    id = "1",
                    title = "আমার প্রিয় গ্রাম",
                    excerpt = "আমার গ্রামের নাম বড় সুন্দর, নদীর পাড়ে ছোট্ট এক গ্রাম। সবুজ মাঠ, ধানের ক্ষেত...",
                    content = """
                        আমার গ্রামের নাম বড় সুন্দর, নদীর পাড়ে ছোট্ট এক গ্রাম। সবুজ মাঠ, ধানের ক্ষেত, নারকেল আর সুপারি গাছের সারি। প্রতিদিন সকালে পাখির ডাকে ঘুম ভাঙে, সন্ধ্যায় নদীর পাড়ে বসে সূর্যাস্ত দেখা যায়।
                        
                        আমার শৈশব কেটেছে এই গ্রামে। নদীতে সাঁতার কাটা, গাছে চড়া, বৃষ্টিতে ভেজা - এসব স্মৃতি মনে পড়ে যখন। গ্রামের মানুষগুলো খুব ভালো, সবাই একে অপরকে চেনে, একসাথে খুশি আর দুঃখ ভাগ করে নেয়।
                        
                        শহরে এসে অনেক কিছু পেয়েছি, কিন্তু গ্রামের সেই সরল জীবনের মতো শান্তি আর কোথাও পাই না। মাঝে মাঝে মনে হয়, ফিরে যাই সেই গ্রামে, প্রকৃতির কাছে।
                    """.trimIndent(),
                    coverImageUrl = "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=800",
                    category = WritingCategory.STORY,
                    authorId = "author1",
                    authorName = "রহিম উদ্দিন",
                    authorProfileImage = null,
                    likes = 42,
                    comments = 5,
                    createdAt = System.currentTimeMillis() - 86400000
                ),
                Writing(
                    id = "2",
                    title = "বৃষ্টির কবিতা",
                    excerpt = "বৃষ্টি এলো মেঘের সাথে, ভিজিয়ে দিল মাটির পাতে...",
                    content = """
                        বৃষ্টি এলো মেঘের সাথে,
                        ভিজিয়ে দিল মাটির পাতে।
                        শিশিরের ফোঁটা ঝরে পড়ে,
                        গাছের পাতায় মুক্তো জড়ে।
                        
                        দূর আকাশে মেঘের রাজা,
                        বৃষ্টি নিয়ে করে সাজা।
                        ঝমঝম শব্দে নামে ঝরে,
                        মনের সুখে গান ধরে।
                        
                        ময়ূর নাচে বনের মাঝে,
                        বৃষ্টির তালে মন আনচায়ে।
                        প্রকৃতি হাসে সবুজ সাজে,
                        বৃষ্টি এলে সবই বাজে।
                    """.trimIndent(),
                    coverImageUrl = "https://images.unsplash.com/photo-1519692933481-e162a57d6721?w=800",
                    category = WritingCategory.POEM,
                    authorId = "author2",
                    authorName = "সালমা খাতুন",
                    authorProfileImage = null,
                    likes = 67,
                    comments = 12,
                    createdAt = System.currentTimeMillis() - 43200000
                ),
                Writing(
                    id = "3",
                    title = "স্মৃতির পাতায়",
                    excerpt = "জীবনের অনেক ঘটনা আছে যা কখনো ভোলা যায় না। আমার বাবার সাথে প্রথম সাইকেল চালানো...",
                    content = """
                        জীবনের অনেক ঘটনা আছে যা কখনো ভোলা যায় না। আমার বাবার সাথে প্রথম সাইকেল চালানো শেখার দিনটি এমনই একটি স্মৃতি।
                        
                        আমার বয়স তখন মাত্র সাত বছর। বাবা একদিন নতুন একটি সাইকেল কিনে আনলেন। লাল রঙের, চকচকে। আমি খুশিতে লাফিয়ে উঠলাম। কিন্তু চালাতে পারি না দেখে মন খারাপ হয়ে গেল।
                        
                        বাবা বললেন, "চিন্তা করিস না, আমি শেখাবো।" পরের দিন থেকে প্রতিদিন বিকেলে বাবা আমাকে সাইকেল চালানো শেখাতে লাগলেন। পিছনে ধরে রাখতেন, আমি প্যাডেল মারতাম।
                        
                        একদিন বাবা হাত ছেড়ে দিলেন, কিন্তু আমি বুঝতেই পারিনি। যখন বুঝলাম তখন আমি একা একা চালাচ্ছি। সেই খুশি, সেই গর্ব - আজও মনে আছে। বাবার হাসিমুখও মনে আছে।
                        
                        বাবা আজ আর নেই, কিন্তু সেই স্মৃতি আমার সাথে চিরদিন থাকবে।
                    """.trimIndent(),
                    coverImageUrl = "https://images.unsplash.com/photo-1485160497022-3e09382fb310?w=800",
                    category = WritingCategory.LIFE_STORIES,
                    authorId = "author3",
                    authorName = "করিম মিয়া",
                    authorProfileImage = null,
                    likes = 89,
                    comments = 23,
                    createdAt = System.currentTimeMillis() - 172800000
                ),
                Writing(
                    id = "4",
                    title = "আমার বন্ধু",
                    excerpt = "বন্ধুত্ব এক অমূল্য সম্পদ। আমার এক বন্ধু ছিল, নাম তার রাকিব...",
                    content = """
                        বন্ধুত্ব এক অমূল্য সম্পদ। আমার এক বন্ধু ছিল, নাম তার রাকিব। স্কুলের প্রথম দিন থেকেই আমরা বন্ধু। একসাথে পড়া, খেলা, দুষ্টুমি - সব করতাম।
                        
                        রাকিব খুব ভালো ছবি আঁকতে পারত। আমি গল্প লিখতাম। একদিন আমরা স্থির করলাম, একসাথে একটা বই বানাবো। আমি গল্প লিখব, রাকিব ছবি আঁকবে।
                        
                        অনেক পরিশ্রম করে আমরা একটা ছোট বই তৈরি করলাম। স্কুলের প্রতিযোগিতায় দিলাম, আর প্রথম পুরস্কার পেলাম। সেদিন আমাদের খুশির সীমা ছিল না।
                        
                        এখন রাকিব বিদেশে থাকে, কিন্তু আমরা এখনও যোগাযোগ রাখি। সত্যিকারের বন্ধুত্ব দূরত্ব মানে না।
                    """.trimIndent(),
                    coverImageUrl = "https://images.unsplash.com/photo-1529156069898-49953e39b3ac?w=800",
                    category = WritingCategory.STORY,
                    authorId = "author1",
                    authorName = "রহিম উদ্দিন",
                    authorProfileImage = null,
                    likes = 34,
                    comments = 7,
                    createdAt = System.currentTimeMillis() - 259200000
                ),
                Writing(
                    id = "5",
                    title = "ছড়া - আম পাকা",
                    excerpt = "আম পাকা, জাম পাকা, পাকা কাঁঠাল...",
                    content = """
                        আম পাকা, জাম পাকা,
                        পাকা কাঁঠাল।
                        গাছে গাছে পাখি ডাকে,
                        দিনের শুরু কাল।
                        
                        মধু মাখা আমের রস,
                        খেতে বড় মজা।
                        বাচ্চারা সবাই মিলে,
                        করি খেলার বাজা।
                        
                        গ্রীষ্মের রোদে তেতে ওঠে,
                        মাটি আর ঘাস।
                        গাছের ছায়ায় বসে থাকি,
                        খাই আম আর তাল পাতার ভাত।
                    """.trimIndent(),
                    coverImageUrl = "https://images.unsplash.com/photo-1601493700631-2b16ec4b4716?w=800",
                    category = WritingCategory.RHYME,
                    authorId = "author2",
                    authorName = "সালমা খাতুন",
                    authorProfileImage = null,
                    likes = 56,
                    comments = 9,
                    createdAt = System.currentTimeMillis() - 345600000
                ),
                Writing(
                    id = "6",
                    title = "বাংলার গান",
                    excerpt = "আমার সোনার বাংলা, আমি তোমায় ভালোবাসি...",
                    content = """
                        আমার সোনার বাংলা,
                        আমি তোমায় ভালোবাসি।
                        চিরদিন তোমার আকাশ,
                        তোমার বাতাস, আমার প্রাণে বাজায় বাঁশি।
                        
                        ও মা, ফাগুনে তোর আমের বনে
                        ঘ্রাণে পাগল করে,
                        মরি হায়, হায় রে—
                        ও মা, অঘ্রানে তোর ভরা ক্ষেতে
                        আমি কী দেখেছি মধুর হাসি।
                        
                        কী শোভা, কী ছায়া গো,
                        কী স্নেহ, কী মায়া গো—
                        কী আঁচল বিছায়েছ
                        বটের মূলে, নদীর কূলে কূলে।
                    """.trimIndent(),
                    coverImageUrl = "https://images.unsplash.com/photo-1511285560929-80b456fea0bc?w=800",
                    category = WritingCategory.SONG,
                    authorId = "author4",
                    authorName = "রবীন্দ্রনাথ ঠাকুর",
                    authorProfileImage = null,
                    likes = 156,
                    comments = 34,
                    createdAt = System.currentTimeMillis() - 432000000
                )
            )
        )

        writings.forEach { writing ->
            commentsMap[writing.id] = mutableListOf()
        }
    }
}

