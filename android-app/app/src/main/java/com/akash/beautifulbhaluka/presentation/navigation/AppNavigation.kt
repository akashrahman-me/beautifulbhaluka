package com.akash.beautifulbhaluka.presentation.navigation

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
import com.akash.beautifulbhaluka.presentation.screens.bookings.BookingsScreen
import com.akash.beautifulbhaluka.presentation.screens.broadband.BroadbandScreen
import com.akash.beautifulbhaluka.presentation.screens.butchercook.ButcherCookScreen
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
import com.akash.beautifulbhaluka.presentation.screens.hospital.HospitalScreen
import com.akash.beautifulbhaluka.presentation.screens.hotels.HotelsScreen
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobsScreen
import com.akash.beautifulbhaluka.presentation.screens.jobs.details.JobDetailsScreen
import com.akash.beautifulbhaluka.presentation.screens.kazioffice.KaziOfficeScreen
import com.akash.beautifulbhaluka.presentation.screens.ladiesparlour.LadiesParlourScreen
import com.akash.beautifulbhaluka.presentation.screens.lawyer.LawyerScreen
import com.akash.beautifulbhaluka.presentation.screens.maps.MapsScreen
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
        startDestination = startDestination
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
        composable(NavigationRoutes.NEWS) {
            NewsScreen()
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
            AmbulanceScreen()
        }
        composable(NavigationRoutes.BLOOD_BANK) {
            BloodBankScreen()
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
            LawyerScreen()
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
            TuitionScreen()
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
            FamousPersonScreen()
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
    }
}
