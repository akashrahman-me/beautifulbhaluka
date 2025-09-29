package com.akash.beautifulbhaluka.domain.usecase

import com.akash.beautifulbhaluka.domain.model.VoterListItem
import com.akash.beautifulbhaluka.domain.repository.VoterListRepository

/**
 * Use case for getting voter list data
 */
class GetVoterListUseCase(
    private val repository: VoterListRepository
) {
    suspend operator fun invoke(): Result<List<VoterListItem>> {
        return repository.getVoterListItems()
    }
}
