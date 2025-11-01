package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.Donor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for filtering blood donors based on various criteria
 * Encapsulates the business logic for donor filtering
 */
@Singleton
class FilterDonorsUseCase @Inject constructor() {

    /**
     * Filter donors by blood group and availability status
     *
     * @param donors List of all donors
     * @param bloodGroup Optional blood group filter (A+, A-, B+, B-, O+, O-, AB+, AB-)
     * @param availability Optional availability status (সময় হয়েছে, সময় হয়নি)
     * @return Filtered list of donors
     */
    operator fun invoke(
        donors: List<Donor>,
        bloodGroup: String? = null,
        availability: String? = null
    ): List<Donor> {
        var filtered = donors

        // Filter by blood group
        bloodGroup?.let { group ->
            filtered = filtered.filter { it.bloodGroup == group }
        }

        // Filter by availability
        availability?.let { status ->
            filtered = when (status) {
                "সময় হয়েছে" -> filtered.filter { it.status == "সময় হয়েছে" }
                "সময় হয়নি" -> filtered.filter { it.status == "সময় হয়নি" }
                else -> filtered
            }
        }

        return filtered
    }

    /**
     * Get all available blood groups for filtering
     */
    fun getAvailableBloodGroups(): List<String> {
        return listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
    }

    /**
     * Get available status options for filtering
     */
    fun getAvailabilityStatuses(): List<String> {
        return listOf("সময় হয়েছে", "সময় হয়নি")
    }
}

