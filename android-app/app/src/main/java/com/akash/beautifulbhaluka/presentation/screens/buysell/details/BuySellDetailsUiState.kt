package com.akash.beautifulbhaluka.presentation.screens.buysell.details

import com.akash.beautifulbhaluka.presentation.screens.buysell.BuySellItem

data class BuySellDetailsUiState(
    val isLoading: Boolean = false,
    val item: BuySellItem? = null,
    val error: String? = null,
    val isFavorite: Boolean = false,
    val isContactingSeller: Boolean = false,
    val selectedImageIndex: Int = 0,
    val showImageViewer: Boolean = false,
    val showReportDialog: Boolean = false,
    val relatedItems: List<BuySellItem> = emptyList()
)

sealed class BuySellDetailsAction {
    data class LoadItem(val itemId: String) : BuySellDetailsAction()
    object ToggleFavorite : BuySellDetailsAction()
    object ContactSeller : BuySellDetailsAction()
    object CallSeller : BuySellDetailsAction()
    object WhatsAppSeller : BuySellDetailsAction()
    object ShareItem : BuySellDetailsAction()
    data class SelectImage(val index: Int) : BuySellDetailsAction()
    object ShowImageViewer : BuySellDetailsAction()
    object HideImageViewer : BuySellDetailsAction()
    object ShowReportDialog : BuySellDetailsAction()
    object HideReportDialog : BuySellDetailsAction()
    object ReportItem : BuySellDetailsAction()
}

