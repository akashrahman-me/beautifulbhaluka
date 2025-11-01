package com.akash.beautifulbhaluka.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Domain model for Blood Donor
 * Following Clean Architecture principles
 */
data class Donor(
    val id: String,
    val name: String,
    val phone: String,
    val bloodGroup: String,
    val location: String,
    val lastDonationDate: LocalDate,
    val facebookLink: String? = null,
    val whatsappNumber: String? = null
) {
    /**
     * Calculate if donor is eligible to donate (3+ months since last donation)
     */
    val isEligible: Boolean
        get() {
            val monthsSinceLastDonation =
                ChronoUnit.MONTHS.between(lastDonationDate, LocalDate.now())
            return monthsSinceLastDonation >= 3
        }

    /**
     * Get status in Bengali
     */
    val status: String
        get() = if (isEligible) "সময় হয়েছে" else "সময় হয়নি"

    /**
     * Get last donation time ago in Bengali
     */
    val lastDonationTimeAgo: String
        get() {
            val now = LocalDate.now()
            val daysSince = ChronoUnit.DAYS.between(lastDonationDate, now)
            val monthsSince = ChronoUnit.MONTHS.between(lastDonationDate, now)

            return when {
                monthsSince >= 1 -> {
                    val months = monthsSince.toInt()
                    val remainingDays = (daysSince - (months * 30)).toInt()
                    if (remainingDays > 0) {
                        "${toBengaliNumber(months)} মাস ${toBengaliNumber(remainingDays)} দিন"
                    } else {
                        "${toBengaliNumber(months)} মাস"
                    }
                }

                daysSince > 0 -> "${toBengaliNumber(daysSince.toInt())} দিন"
                else -> "আজ"
            }
        }

    /**
     * Get last donation date formatted in Bengali (DD/MM/YYYY)
     */
    val lastDonationDateFormatted: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val dateStr = lastDonationDate.format(formatter)
            return toBengaliNumber(dateStr)
        }

    private fun toBengaliNumber(number: Int): String = toBengaliNumber(number.toString())

    private fun toBengaliNumber(text: String): String {
        return text.map { char ->
            when (char) {
                '0' -> '০'
                '1' -> '১'
                '2' -> '২'
                '3' -> '৩'
                '4' -> '৪'
                '5' -> '৫'
                '6' -> '৬'
                '7' -> '৭'
                '8' -> '৮'
                '9' -> '৯'
                else -> char
            }
        }.joinToString("")
    }
}

