package com.akash.beautifulbhaluka.data.repository

import com.akash.beautifulbhaluka.domain.model.Donor
import com.akash.beautifulbhaluka.domain.repository.BloodBankRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of BloodBankRepository
 * Following Clean Architecture principles
 *
 * TODO: Replace with actual Firebase/API implementation
 */
@Singleton
class BloodBankRepositoryImpl @Inject constructor() : BloodBankRepository {

    private val _donors = MutableStateFlow(generateDefaultDonors())
    private val donors: Flow<List<Donor>> = _donors.asStateFlow()

    private val myPublishedDonors = MutableStateFlow<List<Donor>>(emptyList())

    init {
        // Initialize with some sample published donors
        myPublishedDonors.value = listOf(
            Donor(
                id = "my-1",
                name = "Kibriya zaman munna",
                phone = "০১৭২৬৭৮৮৮৮",
                bloodGroup = "A+",
                location = "মাসতালবেড়ি, বালুকা",
                lastDonationDate = LocalDate.now().minusMonths(3),
                facebookLink = "https://facebook.com/kibriya",
                whatsappNumber = "০১৭২৬৭৮৮৮৮"
            ),
            Donor(
                id = "my-2",
                name = "আরিফুল ইসলাম",
                phone = "০১৮১২৩৪৫৬৭৮",
                bloodGroup = "B+",
                location = "ভালুকা পৌরসভা",
                lastDonationDate = LocalDate.now().minusMonths(1),
                facebookLink = null,
                whatsappNumber = "০১৮১২৩৪৫৬৭৮"
            )
        )
    }

    override suspend fun getDonors(): Result<List<Donor>> {
        return try {
            delay(300) // Simulate network delay
            Result.success(_donors.value)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDonorById(id: String): Result<Donor> {
        return try {
            delay(200)
            val donor = _donors.value.find { it.id == id }
            if (donor != null) {
                Result.success(donor)
            } else {
                Result.failure(Exception("Donor not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDonorsByBloodGroup(bloodGroup: String): Result<List<Donor>> {
        return try {
            delay(300)
            val filtered = _donors.value.filter { it.bloodGroup == bloodGroup }
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyPublishedDonors(): Result<List<Donor>> {
        return try {
            delay(300)
            Result.success(myPublishedDonors.value)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun publishDonor(donor: Donor): Result<Donor> {
        return try {
            delay(500)
            myPublishedDonors.value = myPublishedDonors.value + donor
            Result.success(donor)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDonor(donor: Donor): Result<Donor> {
        return try {
            delay(500)
            myPublishedDonors.value = myPublishedDonors.value.map {
                if (it.id == donor.id) donor else it
            }
            Result.success(donor)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDonor(donorId: String): Result<Unit> {
        return try {
            delay(800)
            myPublishedDonors.value = myPublishedDonors.value.filter { it.id != donorId }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchDonors(query: String): Result<List<Donor>> {
        return try {
            delay(300)
            val filtered = _donors.value.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true)
            }
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeDonors(): Flow<List<Donor>> = donors

    /**
     * Generate default donors with realistic dates based on current date
     */
    private fun generateDefaultDonors(): List<Donor> {
        val now = LocalDate.now() // October 31, 2025

        return listOf(
            Donor(
                id = "1",
                name = "Kibriya zaman munna",
                phone = "০১৭২৬৭৮৮৮৮",
                bloodGroup = "A+",
                location = "মাসতালবেড়ি, বালুকা",
                lastDonationDate = now.minusMonths(5)
                    .minusDays(24), // ~5 months 24 days ago - Eligible
                facebookLink = "https://facebook.com/kibriya",
                whatsappNumber = "০১৭২৬৭৮৮৮৮"
            ),
            Donor(
                id = "2",
                name = "রাকিব হাসান",
                phone = "০১৮১২৩৪৫৬৭৮",
                bloodGroup = "B+",
                location = "ভালুকা পৌরসভা",
                lastDonationDate = now.minusMonths(1)
                    .minusDays(10), // ~1 month 10 days ago - Not eligible
                facebookLink = "https://facebook.com/rakib",
                whatsappNumber = null
            ),
            Donor(
                id = "3",
                name = "সাকিব আহমেদ",
                phone = "০১৭১৫৬৭৮৯০১",
                bloodGroup = "O+",
                location = "রামপুর, ভালুকা",
                lastDonationDate = now.minusMonths(4)
                    .minusDays(5), // ~4 months 5 days ago - Eligible
                facebookLink = null,
                whatsappNumber = "০১৭১৫৬৭৮৯০১"
            ),
            Donor(
                id = "4",
                name = "তানভীর আলম",
                phone = "০১৯২৩৪৫৬৭৮৯",
                bloodGroup = "AB+",
                location = "ভালুকা বাজার",
                lastDonationDate = now.minusMonths(2)
                    .minusDays(15), // ~2 months 15 days ago - Not eligible
                facebookLink = "https://facebook.com/tanvir",
                whatsappNumber = "০১৯২৩৪৫৬৭৮৯"
            ),
            Donor(
                id = "5",
                name = "ফারহান ইসলাম",
                phone = "০১৩১২৩৪৫৬৭৮",
                bloodGroup = "A-",
                location = "মাসতালবেড়ি, বালুকা",
                lastDonationDate = now.minusMonths(5)
                    .minusDays(2), // ~5 months 2 days ago - Eligible
                facebookLink = "https://facebook.com/farhan",
                whatsappNumber = "০১৩১২৩৪৫৬৭৮"
            )
        )
    }
}

