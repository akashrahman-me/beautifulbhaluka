package com.akash.beautifulbhaluka.domain.repository

import com.akash.beautifulbhaluka.domain.model.VoterListItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for voter list data operations
 */
interface VoterListRepository {
    /**
     * Get all voter list items
     */
    suspend fun getVoterListItems(): Result<List<VoterListItem>>

    /**
     * Observe voter list items changes
     */
    fun observeVoterListItems(): Flow<List<VoterListItem>>
}
