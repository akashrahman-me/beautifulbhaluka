package com.akash.beautifulbhaluka.domain.repository

import com.akash.beautifulbhaluka.domain.model.Donor
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Blood Bank operations
 * Following Clean Architecture principles
 */
interface BloodBankRepository {

    /**
     * Get all available donors
     */
    suspend fun getDonors(): Result<List<Donor>>

    /**
     * Get donor by ID
     */
    suspend fun getDonorById(id: String): Result<Donor>

    /**
     * Get donors by blood group
     */
    suspend fun getDonorsByBloodGroup(bloodGroup: String): Result<List<Donor>>

    /**
     * Get user's published donor profiles
     */
    suspend fun getMyPublishedDonors(): Result<List<Donor>>

    /**
     * Publish new donor profile
     */
    suspend fun publishDonor(donor: Donor): Result<Donor>

    /**
     * Update donor profile
     */
    suspend fun updateDonor(donor: Donor): Result<Donor>

    /**
     * Delete donor profile
     */
    suspend fun deleteDonor(donorId: String): Result<Unit>

    /**
     * Search donors by name or location
     */
    suspend fun searchDonors(query: String): Result<List<Donor>>

    /**
     * Observe donors changes
     */
    fun observeDonors(): Flow<List<Donor>>
}

