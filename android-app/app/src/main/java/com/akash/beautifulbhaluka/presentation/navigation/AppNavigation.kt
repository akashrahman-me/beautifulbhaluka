package com.akash.beautifulbhaluka.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.akash.beautifulbhaluka.presentation.screens.about.AboutScreen
import com.akash.beautifulbhaluka.presentation.screens.accommodation.AccommodationScreen
import com.akash.beautifulbhaluka.presentation.screens.achiever.AchieverScreen
import com.akash.beautifulbhaluka.presentation.screens.activities.ActivitiesScreen
import com.akash.beautifulbhaluka.presentation.screens.airtravel.AirTravelScreen
import com.akash.beautifulbhaluka.presentation.screens.ambulance.AmbulanceScreen
import com.akash.beautifulbhaluka.presentation.screens.attractions.AttractionsScreen
import com.akash.beautifulbhaluka.presentation.screens.bank.BankScreen
import com.akash.beautifulbhaluka.presentation.screens.bloodbank.BloodBankScreen
import com.akash.beautifulbhaluka.presentation.screens.bloodbank.guidelines.BloodDonationGuidelinesScreen
import com.akash.beautifulbhaluka.presentation.screens.bloodbank.publish.PublishDonorScreen
import com.akash.beautifulbhaluka.presentation.screens.bloodbank.manage.ManageDonorsScreen
import com.akash.beautifulbhaluka.presentation.screens.bookings.BookingsScreen
import com.akash.beautifulbhaluka.presentation.screens.broadband.BroadbandScreen
import com.akash.beautifulbhaluka.presentation.screens.butchercook.ButcherCookScreen
import com.akash.beautifulbhaluka.presentation.screens.buysell.BuySellScreen
import com.akash.beautifulbhaluka.presentation.screens.buysell.details.BuySellDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.buysell.publish.PublishBuySellScreen
import com.akash.beautifulbhaluka.presentation.screens.calculator.CalculatorScreen
import com.akash.beautifulbhaluka.presentation.screens.carrent.CarRentScreen
import com.akash.beautifulbhaluka.presentation.screens.cleaner.CleanerScreen
import com.akash.beautifulbhaluka.presentation.screens.courier.CourierScreen
import com.akash.beautifulbhaluka.presentation.screens.craftsman.CraftsmanScreen
import com.akash.beautifulbhaluka.presentation.screens.culture.CultureScreen
import com.akash.beautifulbhaluka.presentation.screens.cyberexpert.CyberExpertScreen
import com.akash.beautifulbhaluka.presentation.screens.directory.DirectoryScreen
import com.akash.beautifulbhaluka.presentation.screens.doctor.DoctorScreen
import com.akash.beautifulbhaluka.presentation.screens.electricity.ElectricityScreen
import com.akash.beautifulbhaluka.presentation.screens.emergency.EmergencyScreen
import com.akash.beautifulbhaluka.presentation.screens.entertainment.EntertainmentScreen
import com.akash.beautifulbhaluka.presentation.screens.events.EventsScreen
import com.akash.beautifulbhaluka.presentation.screens.famouspeople.FamousPersonScreen
import com.akash.beautifulbhaluka.presentation.screens.famouspeople.details.FamousPersonDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.festivals.FestivalsScreen
import com.akash.beautifulbhaluka.presentation.screens.fireservice.FireServiceScreen
import com.akash.beautifulbhaluka.presentation.screens.food.FoodScreen
import com.akash.beautifulbhaluka.presentation.screens.freedomfighter.FreedomFighterScreen
import com.akash.beautifulbhaluka.presentation.screens.gallery.GalleryScreen
import com.akash.beautifulbhaluka.presentation.screens.gentsparlour.GentsParlourScreen
import com.akash.beautifulbhaluka.presentation.screens.guides.GuidesScreen
import com.akash.beautifulbhaluka.presentation.screens.gym.GymScreen
import com.akash.beautifulbhaluka.presentation.screens.help.HelpScreen
import com.akash.beautifulbhaluka.presentation.screens.heritage.HeritageScreen
import com.akash.beautifulbhaluka.presentation.screens.history.HistoryScreen
import com.akash.beautifulbhaluka.presentation.screens.home.HomeScreen
import com.akash.beautifulbhaluka.presentation.screens.houserent.details.HouseRentDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.houserent.publish.PublishHouseRentScreen
import com.akash.beautifulbhaluka.presentation.screens.hospital.HospitalScreen
import com.akash.beautifulbhaluka.presentation.screens.hotels.HotelsScreen
import com.akash.beautifulbhaluka.presentation.screens.houserent.HouseRentScreen
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobsScreen
import com.akash.beautifulbhaluka.presentation.screens.jobs.details.JobDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.kazioffice.KaziOfficeScreen
import com.akash.beautifulbhaluka.presentation.screens.ladiesparlour.LadiesParlourScreen
import com.akash.beautifulbhaluka.presentation.screens.lawyer.LawyerScreen
import com.akash.beautifulbhaluka.presentation.screens.lawyer.publish.PublishLawyerScreen
import com.akash.beautifulbhaluka.presentation.screens.maps.MapsScreen
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.MatchmakingScreen
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.details.MatchmakingDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.publish.PublishMatchmakingScreen
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.details.MatchmakerDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.publish.PublishMatchmakerScreen
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.manage.ManageMatchmakerScreen
import com.akash.beautifulbhaluka.presentation.screens.museums.MuseumsScreen
import com.akash.beautifulbhaluka.presentation.screens.news.NewsScreen
import com.akash.beautifulbhaluka.presentation.screens.notifications.NotificationsScreen
import com.akash.beautifulbhaluka.presentation.screens.places.PlacesScreen
import com.akash.beautifulbhaluka.presentation.screens.police.PoliceScreen
import com.akash.beautifulbhaluka.presentation.screens.pourashava.PourashavaScreen
import com.akash.beautifulbhaluka.presentation.screens.pressgraphics.PressGraphicsScreen
import com.akash.beautifulbhaluka.presentation.screens.profile.ProfileScreen
import com.akash.beautifulbhaluka.presentation.screens.restaurants.RestaurantsScreen
import com.akash.beautifulbhaluka.presentation.screens.reviews.ReviewsScreen
import com.akash.beautifulbhaluka.presentation.screens.schoolcollege.SchoolCollegeScreen
import com.akash.beautifulbhaluka.presentation.screens.services.ServicesScreen
import com.akash.beautifulbhaluka.presentation.screens.settings.SettingsScreen
import com.akash.beautifulbhaluka.presentation.screens.shopping.ShoppingScreen
import com.akash.beautifulbhaluka.presentation.screens.shops.ShopsScreen
import com.akash.beautifulbhaluka.presentation.screens.shops.details.ProductDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.shops.publish.PublishProductScreen
import com.akash.beautifulbhaluka.presentation.screens.social.SocialScreen
import com.akash.beautifulbhaluka.presentation.screens.tourism.TourismScreen
import com.akash.beautifulbhaluka.presentation.screens.tours.ToursScreen
import com.akash.beautifulbhaluka.presentation.screens.transport.TransportScreen
import com.akash.beautifulbhaluka.presentation.screens.tuition.TuitionScreen
import com.akash.beautifulbhaluka.presentation.screens.tuition.details.TuitionDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.tuition.publish.PublishTutorScreen
import com.akash.beautifulbhaluka.presentation.screens.union.UnionScreen
import com.akash.beautifulbhaluka.presentation.screens.upazila.UpazilaScreen
import com.akash.beautifulbhaluka.presentation.screens.upazila_admin.UpazilaAdminScreen
import com.akash.beautifulbhaluka.presentation.screens.voterlist.VoterListScreen
import com.akash.beautifulbhaluka.presentation.screens.weather.WeatherScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = NavigationRoutes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        // Home Screen
        composable(NavigationRoutes.HOME) {
            HomeScreen(
                onNavigateToScreen = { route ->
                    navController.navigate(route)
                }
            )
        }

        // Tourism screens
        composable(NavigationRoutes.PLACES) {
            PlacesScreen()
        }
        composable(NavigationRoutes.ATTRACTIONS) {
            AttractionsScreen()
        }
        composable(NavigationRoutes.TOURS) {
            ToursScreen()
        }
        composable(NavigationRoutes.TOURISM) {
            TourismScreen()
        }
        composable(NavigationRoutes.MAPS) {
            MapsScreen()
        }
        composable(NavigationRoutes.GUIDES) {
            GuidesScreen()
        }

        // Accommodation & Services
        composable(NavigationRoutes.HOTELS) {
            HotelsScreen()
        }
        composable(NavigationRoutes.ACCOMMODATION) {
            AccommodationScreen()
        }
        composable(NavigationRoutes.HOUSE_RENT) {
            HouseRentScreen(
                onNavigateToDetails = { propertyId ->
                    navController.navigate("house_rent_details/$propertyId")
                },
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.HOUSE_RENT_PUBLISH)
                }
            )
        }
        composable(
            route = NavigationRoutes.HOUSE_RENT_DETAILS,
            arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            HouseRentDetailsScreen(
                propertyId = propertyId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(NavigationRoutes.HOUSE_RENT_PUBLISH) {
            PublishHouseRentScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(NavigationRoutes.RESTAURANTS) {
            RestaurantsScreen()
        }
        composable(NavigationRoutes.FOOD) {
            FoodScreen()
        }
        composable(NavigationRoutes.SERVICES) {
            ServicesScreen()
        }

        // Culture & Heritage
        composable(NavigationRoutes.CULTURE) {
            CultureScreen()
        }
        composable(NavigationRoutes.HERITAGE) {
            HeritageScreen()
        }
        composable(NavigationRoutes.MUSEUMS) {
            MuseumsScreen()
        }
        composable(NavigationRoutes.FESTIVALS) {
            FestivalsScreen()
        }
        composable(NavigationRoutes.HISTORY) {
            HistoryScreen()
        }

        // Activities & Entertainment
        composable(NavigationRoutes.EVENTS) {
            EventsScreen()
        }
        composable(NavigationRoutes.ACTIVITIES) {
            ActivitiesScreen()
        }
        composable(NavigationRoutes.ENTERTAINMENT) {
            EntertainmentScreen()
        }

        // Practical Information
        composable(NavigationRoutes.TRANSPORT) {
            TransportScreen()
        }
        composable(NavigationRoutes.WEATHER) {
            WeatherScreen()
        }
        composable(NavigationRoutes.EMERGENCY) {
            EmergencyScreen()
        }
        composable(NavigationRoutes.DIRECTORY) {
            DirectoryScreen()
        }

        // Commerce & Media
        composable(NavigationRoutes.SHOPPING) {
            ShoppingScreen()
        }
        composable(NavigationRoutes.GALLERY) {
            GalleryScreen()
        }
        composable(NavigationRoutes.REVIEWS) {
            ReviewsScreen()
        }
        composable(NavigationRoutes.BOOKINGS) {
            BookingsScreen()
        }

        // Buy-Sell Marketplace
        composable(NavigationRoutes.BUY_SELL) {
            BuySellScreen(
                onNavigateToDetails = { itemId ->
                    navController.navigate(NavigationRoutes.buySellDetails(itemId))
                },
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.BUY_SELL_PUBLISH)
                }
            )
        }

        // Existing screens
        composable(NavigationRoutes.JOBS) {
            JobsScreen(
                onNavigateToJobDetails = { jobId ->
                    navController.navigate(NavigationRoutes.jobDetails(jobId))
                },
                onNavigateToPublishJob = {
                    navController.navigate(NavigationRoutes.PUBLISH_JOB)
                }
            )
        }
        composable(NavigationRoutes.SHOPS) {
            ShopsScreen(
                onNavigateToDetails = { productId ->
                    navController.navigate(NavigationRoutes.productDetails(productId))
                },
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.PRODUCT_PUBLISH)
                }
            )
        }
        composable(NavigationRoutes.SOCIAL) {
            SocialScreen(
                onExit = {
                    navController.popBackStack()
                }
            )
        }
        composable(NavigationRoutes.ABOUT) {
            AboutScreen()
        }

        // Administrative levels
        composable(NavigationRoutes.UPAZILA) {
            UpazilaScreen()
        }
        composable(NavigationRoutes.POURASHAVA) {
            PourashavaScreen()
        }
        composable(NavigationRoutes.UNION) {
            UnionScreen()
        }

        // Emergency Services
        composable(NavigationRoutes.FIRE_SERVICE) {
            FireServiceScreen()
        }
        composable(NavigationRoutes.POLICE) {
            PoliceScreen()
        }
        composable(NavigationRoutes.AMBULANCE) {
            AmbulanceScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(NavigationRoutes.BLOOD_BANK) {
            BloodBankScreen(
                onNavigateToGuidelines = {
                    navController.navigate(NavigationRoutes.BLOOD_DONATION_GUIDELINES)
                },
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.BLOOD_BANK_PUBLISH)
                },
                onNavigateToManage = {
                    navController.navigate(NavigationRoutes.BLOOD_BANK_MANAGE)
                }
            )
        }
        composable(NavigationRoutes.BLOOD_DONATION_GUIDELINES) {
            BloodDonationGuidelinesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(NavigationRoutes.BLOOD_BANK_PUBLISH) {
            PublishDonorScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onPublishSuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable(NavigationRoutes.BLOOD_BANK_MANAGE) {
            ManageDonorsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEditDonor = { donorId ->
                    // TODO: Navigate to edit screen
                }
            )
        }

        // Healthcare Services
        composable(NavigationRoutes.DOCTOR) {
            DoctorScreen()
        }
        composable(NavigationRoutes.HOSPITAL) {
            HospitalScreen()
        }

        // Legal & Administrative Services
        composable(NavigationRoutes.LAWYER) {
            LawyerScreen(
                navigateToPublish = {
                    navController.navigate(NavigationRoutes.LAWYER_PUBLISH)
                },
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(NavigationRoutes.LAWYER_PUBLISH) {
            PublishLawyerScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(NavigationRoutes.UPAZILA_ADMIN) {
            UpazilaAdminScreen()
        }
        composable(NavigationRoutes.VOTER_LIST) {
            VoterListScreen()
        }
        composable(NavigationRoutes.KAZI_OFFICE) {
            KaziOfficeScreen()
        }

        // Educational Services
        composable(NavigationRoutes.SCHOOL_COLLEGE) {
            SchoolCollegeScreen()
        }
        composable(NavigationRoutes.TUITION) {
            TuitionScreen(
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.TUITION_PUBLISH)
                },
                onNavigateToDetails = { tutorId ->
                    navController.navigate(NavigationRoutes.getTuitionDetailsRoute(tutorId))
                }
            )
        }

        composable(NavigationRoutes.TUITION_PUBLISH) {
            PublishTutorScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = NavigationRoutes.TUITION_DETAILS,
            arguments = listOf(navArgument("tutorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getString("tutorId") ?: ""
            TuitionDetailsScreen(
                tutorId = tutorId,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        // Financial Services
        composable(NavigationRoutes.BANK) {
            BankScreen()
        }
        composable(NavigationRoutes.COURIER) {
            CourierScreen()
        }

        // Utility Services
        composable(NavigationRoutes.BROADBAND) {
            BroadbandScreen()
        }
        composable(NavigationRoutes.ELECTRICITY) {
            ElectricityScreen()
        }
        composable(NavigationRoutes.CLEANER) {
            CleanerScreen()
        }

        // Beauty & Wellness
        composable(NavigationRoutes.LADIES_PARLOUR) {
            LadiesParlourScreen()
        }
        composable(NavigationRoutes.GENTS_PARLOUR) {
            GentsParlourScreen()
        }
        composable(NavigationRoutes.GYM) {
            GymScreen()
        }

        // Professional Services
        composable(NavigationRoutes.CRAFTSMAN) {
            CraftsmanScreen()
        }
        composable(NavigationRoutes.BUTCHER_COOK) {
            ButcherCookScreen()
        }
        composable(NavigationRoutes.PRESS_GRAPHICS) {
            PressGraphicsScreen()
        }
        composable(NavigationRoutes.CYBER_EXPERT) {
            CyberExpertScreen()
        }
        composable(NavigationRoutes.CAR_RENT) {
            CarRentScreen()
        }
        composable(NavigationRoutes.AIR_TRAVEL) {
            AirTravelScreen()
        }

        // Information Services
        composable(NavigationRoutes.FAMOUS_PERSON) {
            FamousPersonScreen(
                navigateBack = { navController.navigateUp() },
                navigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(
                            NavigationRoutes.HOME
                        ) { inclusive = true }
                    }
                },
                navigateToDetails = { personTitle ->
                    navController.navigate(NavigationRoutes.famousPersonDetails(personTitle))
                }
            )
        }
        composable(
            route = NavigationRoutes.FAMOUS_PERSON_DETAILS,
            arguments = listOf(navArgument("personTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val personTitle = backStackEntry.arguments?.getString("personTitle") ?: ""
            FamousPersonDetailsScreen(
                personTitle = personTitle,
                navigateBack = { navController.navigateUp() },
                navigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(
                            NavigationRoutes.HOME
                        ) { inclusive = true }
                    }
                }
            )
        }
        composable(NavigationRoutes.FREEDOM_FIGHTER) {
            FreedomFighterScreen()
        }
        composable(NavigationRoutes.ACHIEVER) {
            AchieverScreen()
        }
        composable(NavigationRoutes.CALCULATOR) {
            CalculatorScreen()
        }

        // Settings and other screens
        composable(NavigationRoutes.SETTINGS) {
            SettingsScreen()
        }

        // Profile and drawer screens
        composable(NavigationRoutes.PROFILE) {
            ProfileScreen()
        }
        composable(NavigationRoutes.NOTIFICATIONS) {
            NotificationsScreen()
        }
        composable(NavigationRoutes.HELP) {
            HelpScreen()
        }

        // Product Details Screen
        composable(
            route = NavigationRoutes.PRODUCT_DETAILS,
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailsScreen(
                productId = productId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Product Publish Screen
        composable(NavigationRoutes.PRODUCT_PUBLISH) {
            PublishProductScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Publish Job Screen
        composable(NavigationRoutes.PUBLISH_JOB) {
            com.akash.beautifulbhaluka.presentation.screens.jobs.publish.PublishJobScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onJobPublished = {
                    navController.popBackStack()
                }
            )
        }

        // Job Details Screen
        composable(
            route = NavigationRoutes.JOB_DETAILS,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
            JobDetailsScreen(
                jobId = jobId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Buy-Sell Details Screen
        composable(
            route = NavigationRoutes.BUY_SELL_DETAILS,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            BuySellDetailsScreen(
                itemId = itemId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Buy-Sell Publish Screen
        composable(NavigationRoutes.BUY_SELL_PUBLISH) {
            PublishBuySellScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Matchmaking & Marriage Platform
        composable(NavigationRoutes.MATCHMAKING) {
            MatchmakingScreen(
                onNavigateToDetails = { profileId ->
                    navController.navigate(NavigationRoutes.matchmakingDetails(profileId))
                },
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.MATCHMAKING_PUBLISH)
                },
                onNavigateToManageProfiles = {
                    navController.navigate(NavigationRoutes.MANAGE_MATCHMAKING_PROFILES)
                },
                onNavigateToMatchmakerDetails = { matchmakerId ->
                    navController.navigate(NavigationRoutes.matchmakerDetails(matchmakerId))
                },
                onNavigateToPublishMatchmaker = {
                    navController.navigate(NavigationRoutes.MATCHMAKER_PUBLISH)
                },
                onNavigateToManageMatchmakers = {
                    navController.navigate(NavigationRoutes.MANAGE_MATCHMAKER_PROFILES)
                }
            )
        }

        // Matchmaking Details Screen
        composable(
            route = NavigationRoutes.MATCHMAKING_DETAILS,
            arguments = listOf(navArgument("profileId") { type = NavType.StringType })
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId") ?: ""
            MatchmakingDetailsScreen(
                profileId = profileId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Matchmaking Publish Screen
        composable(NavigationRoutes.MATCHMAKING_PUBLISH) {
            PublishMatchmakingScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Manage Matchmaking Profiles Screen
        composable(NavigationRoutes.MANAGE_MATCHMAKING_PROFILES) {
            com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.manage.ManageProfilesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEditProfile = { profileId ->
                    // TODO: Add edit profile navigation when edit screen is ready
                    navController.navigate(NavigationRoutes.matchmakingDetails(profileId))
                },
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.MATCHMAKING_PUBLISH)
                }
            )
        }

        // Matchmaker Details Screen
        composable(
            route = NavigationRoutes.MATCHMAKER_DETAILS,
            arguments = listOf(navArgument("matchmakerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val matchmakerId = backStackEntry.arguments?.getString("matchmakerId") ?: ""
            MatchmakerDetailsScreen(
                matchmakerId = matchmakerId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Matchmaker Publish Screen
        composable(NavigationRoutes.MATCHMAKER_PUBLISH) {
            PublishMatchmakerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Manage Matchmaker Profiles Screen
        composable(NavigationRoutes.MANAGE_MATCHMAKER_PROFILES) {
            ManageMatchmakerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { matchmakerId ->
                    navController.navigate(NavigationRoutes.matchmakerDetails(matchmakerId))
                },
                onNavigateToPublish = {
                    navController.navigate(NavigationRoutes.MATCHMAKER_PUBLISH)
                }
            )
        }

        // News Screen
        composable(NavigationRoutes.NEWS) {
            NewsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToWebView = { url, title ->
                    navController.navigate(NavigationRoutes.webview(url, title))
                }
            )
        }

        // WebView Screen
        composable(
            route = NavigationRoutes.WEBVIEW,
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = "Article"
                }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")?.let {
                java.net.URLDecoder.decode(it, "UTF-8")
            } ?: ""
            val title = backStackEntry.arguments?.getString("title")?.let {
                java.net.URLDecoder.decode(it, "UTF-8")
            } ?: "Article"

            com.akash.beautifulbhaluka.presentation.screens.webview.WebViewScreen(
                url = url,
                title = title,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
