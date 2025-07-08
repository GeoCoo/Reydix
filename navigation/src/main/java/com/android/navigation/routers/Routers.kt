package com.android.navigation.routers

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.common.helpers.serialize
import com.android.details_screen.ui.DetailsScreen
import com.android.main_screen.ui.MainScreen
import com.android.navigation.api.Screen
import com.google.gson.Gson

fun NavGraphBuilder.mainScreenRouter(
    navController: NavHostController,
) {
    composable(route = Screen.MainScreen.route) {
        MainScreen(
            onEventClick = { trainers ->
                val info = trainers.serialize(Gson())
                navController.navigate(
                    Screen.DetailsScreen.createRoute(
                        info = info
                    )
                )
            }
        )
    }
}

fun NavGraphBuilder.detailsScreenRouter(
    navController: NavHostController,
) {
    composable(
        route = Screen.DetailsScreen.route,
        arguments = listOf(
            navArgument("info") { type = NavType.StringType })
    ) { backStackEntry ->
        val event = backStackEntry.arguments?.getString("info") ?: return@composable
        DetailsScreen(info = event) {
            navController.navigateUp()
        }
    }
}

