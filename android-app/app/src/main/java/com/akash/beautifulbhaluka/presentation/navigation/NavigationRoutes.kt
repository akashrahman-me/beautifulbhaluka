package com.akash.beautifulbhaluka.presentation.navigation

object NavigationRoutes {
    const val HOME = "home"
    const val SOCIAL = "social"
    const val JOBS = "jobs"
    const val SHOPS = "shops"
    const val MENU = "menu"

    // Drawer routes
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val NOTIFICATIONS = "notifications"
    const val ABOUT = "about"
    const val HELP = "help"

    // Jobs related routes
    const val JOB_DETAILS = "job_details/{jobId}"
    const val PUBLISH_JOB = "publish_job"

    // Shops related routes
    const val PRODUCT_DETAILS = "product_details/{productId}"
    const val PUBLISH_PRODUCT = "publish_product"

    fun jobDetails(jobId: String): String = "job_details/$jobId"
    fun productDetails(productId: String): String = "product_details/$productId"
}
