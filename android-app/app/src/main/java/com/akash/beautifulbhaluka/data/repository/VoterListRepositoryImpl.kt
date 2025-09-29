package com.akash.beautifulbhaluka.data.repository

import com.akash.beautifulbhaluka.domain.model.VoterListItem
import com.akash.beautifulbhaluka.domain.repository.VoterListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Implementation of VoterListRepository
 * Currently uses static data, can be extended to use remote/local data sources
 */
class VoterListRepositoryImpl : VoterListRepository {

    companion object {
        @Volatile
        private var INSTANCE: VoterListRepositoryImpl? = null

        fun getInstance(): VoterListRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: VoterListRepositoryImpl().also { INSTANCE = it }
            }
        }
    }

    private val voterListData = listOf(
        VoterListItem(
            id = "1",
            unionName = "১নং উথুরা ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Uthura-Union.zip"
        ),
        VoterListItem(
            id = "2",
            unionName = "২নং মেদুয়ারী ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Meduary-Union.zip"
        ),
        VoterListItem(
            id = "3",
            unionName = "৩নং ভরাডোবা ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Bharadoba-Union.zip"
        ),
        VoterListItem(
            id = "4",
            unionName = "৪নং ধীতপুর ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Dhitpur-Union.zip"
        ),
        VoterListItem(
            id = "5",
            unionName = "৫নং বিরুনিয়া ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/BIRONIA-UNION.zip"
        ),
        VoterListItem(
            id = "6",
            unionName = "৬নং ভালুকা ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Bhaluka-Union.zip"
        ),
        VoterListItem(
            id = "7",
            unionName = "৭নং মল্লিকবাড়ী ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Mollikbari-Union.zip"
        ),
        VoterListItem(
            id = "8",
            unionName = "৮নং ডাকাতিয়া ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Dhakatia-Union.zip"
        ),
        VoterListItem(
            id = "9",
            unionName = "৯নং কাচিনা ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Kachina-Union.zip"
        ),
        VoterListItem(
            id = "10",
            unionName = "১০নং হবিরবাড়ী ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Habirbari-Union.zip"
        ),
        VoterListItem(
            id = "11",
            unionName = "১১নং রাজৈ ইউনিয়ন",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Rajoy-Union.zip"
        ),
        VoterListItem(
            id = "12",
            unionName = "ভালুকা পৌরসভা",
            downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Pouroshoba.zip"
        )
    )

    override suspend fun getVoterListItems(): Result<List<VoterListItem>> {
        return try {
            // Simulate network delay
            kotlinx.coroutines.delay(300)
            Result.success(voterListData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeVoterListItems(): Flow<List<VoterListItem>> {
        return flowOf(voterListData)
    }
}
