package com.akash.beautifulbhaluka.data.repository

import com.akash.beautifulbhaluka.domain.model.TuitionPost
import com.akash.beautifulbhaluka.domain.model.TuitionRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class TuitionRepositoryImpl {

    private val _tutors = MutableStateFlow(generateMockTutors())
    private val _requests = MutableStateFlow(generateMockRequests())

    suspend fun getTutors(): Result<List<TuitionPost>> {
        delay(800)
        return Result.success(_tutors.value)
    }

    suspend fun getTutorById(id: String): Result<TuitionPost> {
        delay(300)
        val tutor = _tutors.value.find { it.id == id }
        return if (tutor != null) {
            Result.success(tutor)
        } else {
            Result.failure(Exception("Tutor not found"))
        }
    }

    suspend fun getRequests(): Result<List<TuitionRequest>> {
        delay(800)
        return Result.success(_requests.value)
    }

    suspend fun toggleFavorite(tutorId: String): Result<Unit> {
        delay(200)
        _tutors.value = _tutors.value.map { tutor ->
            if (tutor.id == tutorId) {
                tutor.copy(isFavorite = !tutor.isFavorite)
            } else {
                tutor
            }
        }
        return Result.success(Unit)
    }

    suspend fun publishTutorPost(post: TuitionPost): Result<Unit> {
        delay(500)
        _tutors.value = listOf(post) + _tutors.value
        return Result.success(Unit)
    }

    suspend fun publishRequest(request: TuitionRequest): Result<Unit> {
        delay(500)
        _requests.value = listOf(request) + _requests.value
        return Result.success(Unit)
    }

    private fun generateMockTutors(): List<TuitionPost> {
        return listOf(
            TuitionPost(
                id = "t1",
                teacherId = "teacher1",
                teacherName = "রহিম উদ্দিন",
                teacherPhoto = "https://i.pravatar.cc/150?img=12",
                teacherRating = 4.8f,
                totalReviews = 156,
                subjects = listOf("Math", "Physics", "Chemistry"),
                classes = listOf("Class 9-10", "Class 11-12"),
                experience = "5 years",
                qualification = "BSc in Physics (DU)",
                preferredLocations = listOf("Mirpur", "Dhanmondi", "Uttara"),
                preferredMedium = listOf("Bangla", "English"),
                fee = "৳5000-8000/month",
                availability = "Evening (6pm-9pm)",
                contactNumber = "01712345678",
                description = "Experienced tutor specializing in Science subjects. Proven track record of improving student grades.",
                verified = true,
                createdAt = System.currentTimeMillis() - 86400000
            ),
            TuitionPost(
                id = "t2",
                teacherId = "teacher2",
                teacherName = "ফাতিমা খাতুন",
                teacherPhoto = "https://i.pravatar.cc/150?img=5",
                teacherRating = 4.9f,
                totalReviews = 203,
                subjects = listOf("English", "Bangla"),
                classes = listOf("Class 6-8", "Class 9-10"),
                experience = "7 years",
                qualification = "MA in English (JU)",
                preferredLocations = listOf("Gulshan", "Banani", "Baridhara"),
                preferredMedium = listOf("English"),
                fee = "৳6000-10000/month",
                availability = "Morning & Evening",
                contactNumber = "01823456789",
                description = "Specialized in English literature and grammar. Helped 100+ students achieve A+ grades.",
                verified = true,
                createdAt = System.currentTimeMillis() - 172800000
            ),
            TuitionPost(
                id = "t3",
                teacherId = "teacher3",
                teacherName = "করিম মিয়া",
                teacherPhoto = "https://i.pravatar.cc/150?img=33",
                teacherRating = 4.7f,
                totalReviews = 89,
                subjects = listOf("Math", "ICT"),
                classes = listOf("Class 1-5", "Class 6-8"),
                experience = "3 years",
                qualification = "BSc in CSE (BUET)",
                preferredLocations = listOf("Mohammadpur", "Shyamoli"),
                preferredMedium = listOf("Bangla", "English"),
                fee = "৳3000-5000/month",
                availability = "Weekends",
                contactNumber = "01934567890",
                description = "Young and energetic tutor focused on building strong fundamentals in Math and ICT.",
                verified = false,
                createdAt = System.currentTimeMillis() - 259200000
            ),
            TuitionPost(
                id = "t4",
                teacherId = "teacher4",
                teacherName = "নাসরিন আক্তার",
                teacherPhoto = "https://i.pravatar.cc/150?img=9",
                teacherRating = 5.0f,
                totalReviews = 124,
                subjects = listOf("Biology", "Chemistry"),
                classes = listOf("Class 11-12", "University"),
                experience = "8 years",
                qualification = "MBBS (DMC)",
                preferredLocations = listOf("Dhanmondi", "New Market"),
                preferredMedium = listOf("English"),
                fee = "৳8000-12000/month",
                availability = "Evening only",
                contactNumber = "01745678901",
                description = "Medical college student offering expert guidance in Biology and Chemistry for medical entrance exams.",
                verified = true,
                createdAt = System.currentTimeMillis() - 345600000
            )
        )
    }

    private fun generateMockRequests(): List<TuitionRequest> {
        return listOf(
            TuitionRequest(
                id = "r1",
                parentName = "আব্দুল্লাহ সরকার",
                parentPhoto = "https://i.pravatar.cc/150?img=52",
                studentClass = "Class 9",
                subjects = listOf("Math", "Physics"),
                preferredGender = "Male",
                numberOfDays = 5,
                salary = "৳5000/month",
                location = "Mirpur-10",
                medium = "Bangla Medium",
                requirements = "Need experienced teacher for SSC preparation",
                contactNumber = "01856789012",
                createdAt = System.currentTimeMillis() - 432000000,
                status = "Active"
            ),
            TuitionRequest(
                id = "r2",
                parentName = "রাজিয়া সুলতানা",
                parentPhoto = "https://i.pravatar.cc/150?img=23",
                studentClass = "Class 6",
                subjects = listOf("English", "Math"),
                preferredGender = "Female",
                numberOfDays = 3,
                salary = "৳3000/month",
                location = "Dhanmondi",
                medium = "English Medium",
                requirements = "Female teacher required for evening classes",
                contactNumber = "01967890123",
                createdAt = System.currentTimeMillis() - 518400000,
                status = "Active"
            ),
            TuitionRequest(
                id = "r3",
                parentName = "মোহাম্মদ আলী",
                parentPhoto = "https://i.pravatar.cc/150?img=67",
                studentClass = "Class 12",
                subjects = listOf("Chemistry", "Biology"),
                preferredGender = "Any",
                numberOfDays = 6,
                salary = "৳8000/month",
                location = "Uttara",
                medium = "English Medium",
                requirements = "Medical admission preparation required",
                contactNumber = "01678901234",
                createdAt = System.currentTimeMillis() - 604800000,
                status = "Active"
            )
        )
    }
}

