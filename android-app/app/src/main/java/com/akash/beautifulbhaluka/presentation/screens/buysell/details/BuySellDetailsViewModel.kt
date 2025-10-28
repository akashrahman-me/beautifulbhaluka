package com.akash.beautifulbhaluka.presentation.screens.buysell.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.presentation.screens.buysell.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class BuySellDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BuySellDetailsUiState())
    val uiState: StateFlow<BuySellDetailsUiState> = _uiState.asStateFlow()

    // Mock data (same as in BuySellViewModel for consistency)
    private val mockItems = listOf(
        BuySellItem(
            id = "1",
            title = "Samsung Galaxy S23 Ultra (512GB)",
            description = "একদম নতুন কন্ডিশনে Samsung Galaxy S23 Ultra। সব এক্সেসরিজ সহ। বক্স, চার্জার, ইয়ারফোন সব আছে। মাত্র ৩ মাস ব্যবহার করা হয়েছে। কোন স্ক্র্যাচ নেই।\n\nবিস্তারিত:\n• ১২GB RAM\n• ৫১২GB Storage\n• ২০০MP Camera\n• Snapdragon 8 Gen 2\n• 5000mAh Battery\n• Original Box & Accessories\n\nদাম সামান্য আলোচনা সাপেক্ষে।",
            price = 95000.0,
            originalPrice = 125000.0,
            imageUrl = "https://picsum.photos/400/300?random=1",
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=1",
                "https://picsum.photos/400/300?random=11",
                "https://picsum.photos/400/300?random=12",
                "https://picsum.photos/400/300?random=13"
            ),
            category = BuySellCategory("2", "মোবাইল ফোন", "Mobile Phones", "Smartphone", 89, 0xFF4CAF50),
            condition = ItemCondition.LIKE_NEW,
            type = ItemType.SELL,
            sellerName = "মোহাম্মদ আলী",
            sellerContact = "+880 1711-123456",
            location = "ভালুকা বাজার, ময়মনসিংহ",
            rating = 4.8f,
            viewCount = 234,
            isFeatured = true,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 86400000L
        ),
        BuySellItem(
            id = "2",
            title = "Honda CB 150R (2022 Model)",
            description = "২০২২ মডেল Honda CB 150R। সম্পূর্ণ পেপার সহ। রেগুলার সার্ভিসিং করা। খুবই ভালো কন্ডিশন। ১৫,০০০ কিমি চলেছে।\n\nবৈশিষ্ট্য:\n• Single Cylinder Engine\n• 149cc\n• Disc Brake (Front & Rear)\n• Digital Meter\n• LED Headlight\n\nজরুরী বিক্রয়। দ্রুত যোগাযোগ করুন।",
            price = 185000.0,
            imageUrl = "https://picsum.photos/400/300?random=2",
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=2",
                "https://picsum.photos/400/300?random=21",
                "https://picsum.photos/400/300?random=22"
            ),
            category = BuySellCategory("4", "বাইক", "Motorbikes", "TwoWheeler", 67, 0xFF9C27B0),
            condition = ItemCondition.GOOD,
            type = ItemType.SELL,
            sellerName = "রহিম মিয়া",
            sellerContact = "+880 1812-345678",
            location = "ভালুকা উপজেলা",
            rating = 4.5f,
            viewCount = 567,
            isFeatured = true,
            isUrgent = true,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 172800000L
        ),
        BuySellItem(
            id = "3",
            title = "Sony 55\" 4K Smart TV",
            description = "Sony 55 inch 4K Smart Android TV। ২ বছর ব্যবহৃত। পারফেক্ট কন্ডিশন। কোন সমস্যা নেই।",
            price = 65000.0,
            originalPrice = 95000.0,
            imageUrl = "https://picsum.photos/400/300?random=3",
            category = BuySellCategory("1", "ইলেকট্রনিক্স", "Electronics", "Devices", 45, 0xFF2196F3),
            condition = ItemCondition.GOOD,
            type = ItemType.SELL,
            sellerName = "করিম সাহেব",
            sellerContact = "+880 1913-456789",
            location = "কদমতলী, ভালুকা",
            rating = 4.2f,
            viewCount = 189,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 259200000L
        )
    )

    fun onAction(action: BuySellDetailsAction) {
        when (action) {
            is BuySellDetailsAction.LoadItem -> loadItem(action.itemId)
            is BuySellDetailsAction.ToggleFavorite -> toggleFavorite()
            is BuySellDetailsAction.ContactSeller -> contactSeller()
            is BuySellDetailsAction.CallSeller -> callSeller()
            is BuySellDetailsAction.WhatsAppSeller -> whatsAppSeller()
            is BuySellDetailsAction.ShareItem -> shareItem()
            is BuySellDetailsAction.SelectImage -> selectImage(action.index)
            is BuySellDetailsAction.ShowImageViewer -> showImageViewer()
            is BuySellDetailsAction.HideImageViewer -> hideImageViewer()
            is BuySellDetailsAction.ShowReportDialog -> showReportDialog()
            is BuySellDetailsAction.HideReportDialog -> hideReportDialog()
            is BuySellDetailsAction.ReportItem -> reportItem()
        }
    }

    private fun loadItem(itemId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(500) // Simulate network delay

            val item = mockItems.find { it.id == itemId }
            if (item != null) {
                // Get related items (same category, excluding current item)
                val related = mockItems.filter {
                    it.category.id == item.category.id && it.id != itemId
                }.take(3)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        item = item,
                        relatedItems = related,
                        error = null
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "আইটেম খুঁজে পাওয়া যায়নি"
                    )
                }
            }
        }
    }

    private fun toggleFavorite() {
        _uiState.update { it.copy(isFavorite = !it.isFavorite) }
        // TODO: Implement actual favorite logic
    }

    private fun contactSeller() {
        viewModelScope.launch {
            _uiState.update { it.copy(isContactingSeller = true) }
            delay(1000)
            _uiState.update { it.copy(isContactingSeller = false) }
            // TODO: Implement actual contact logic
        }
    }

    private fun callSeller() {
        // TODO: Implement call logic
    }

    private fun whatsAppSeller() {
        // TODO: Implement WhatsApp logic
    }

    private fun shareItem() {
        // TODO: Implement share logic
    }

    private fun selectImage(index: Int) {
        _uiState.update { it.copy(selectedImageIndex = index) }
    }

    private fun showImageViewer() {
        _uiState.update { it.copy(showImageViewer = true) }
    }

    private fun hideImageViewer() {
        _uiState.update { it.copy(showImageViewer = false) }
    }

    private fun showReportDialog() {
        _uiState.update { it.copy(showReportDialog = true) }
    }

    private fun hideReportDialog() {
        _uiState.update { it.copy(showReportDialog = false) }
    }

    private fun reportItem() {
        viewModelScope.launch {
            hideReportDialog()
            delay(500)
            // TODO: Implement report logic
        }
    }
}

