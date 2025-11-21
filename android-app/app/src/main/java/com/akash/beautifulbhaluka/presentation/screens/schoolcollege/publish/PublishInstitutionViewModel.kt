package com.akash.beautifulbhaluka.presentation.screens.schoolcollege.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishInstitutionViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PublishInstitutionUiState())
    val uiState: StateFlow<PublishInstitutionUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishInstitutionAction) {
        when (action) {
            is PublishInstitutionAction.UpdateImage -> {
                _uiState.update { it.copy(imageUri = action.uri) }
            }

            is PublishInstitutionAction.UpdateName -> {
                _uiState.update { it.copy(name = action.name) }
            }

            is PublishInstitutionAction.UpdateCategory -> {
                _uiState.update { it.copy(category = action.category) }
            }

            is PublishInstitutionAction.UpdateScope -> {
                _uiState.update { it.copy(scope = action.scope) }
            }

            is PublishInstitutionAction.UpdateEstablishedYear -> {
                _uiState.update { it.copy(establishedYear = action.year) }
            }

            is PublishInstitutionAction.UpdateEiin -> {
                _uiState.update { it.copy(eiin = action.eiin) }
            }

            is PublishInstitutionAction.UpdateLocation -> {
                _uiState.update { it.copy(location = action.location) }
            }

            is PublishInstitutionAction.UpdateMobile -> {
                _uiState.update { it.copy(mobile = action.mobile) }
            }

            is PublishInstitutionAction.UpdateDescription -> {
                _uiState.update { it.copy(description = action.description) }
            }

            is PublishInstitutionAction.Submit -> submitInstitution()
        }
    }

    private fun submitInstitution() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }

            try {
                val currentState = _uiState.value

                if (currentState.name.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            error = "প্রতিষ্ঠানের নাম প্রয়োজন"
                        )
                    }
                    return@launch
                }

                if (currentState.establishedYear.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            error = "প্রতিষ্ঠার সাল প্রয়োজন"
                        )
                    }
                    return@launch
                }

                if (currentState.mobile.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            error = "মোবাইল নাম্বার প্রয়োজন"
                        )
                    }
                    return@launch
                }

                // TODO: Implement actual submission logic
                // For now, simulate success
                kotlinx.coroutines.delay(1500)

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        success = true,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        error = "প্রতিষ্ঠান যোগ করতে সমস্যা হয়েছে"
                    )
                }
            }
        }
    }
}

