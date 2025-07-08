package com.android.navigation.api

sealed class Screen(val route: String) {
    object MainScreen : Screen("mainScreen")
    object DetailsScreen : Screen("detailsScreen/{info}") {
        fun createRoute(info: String,) = "detailsScreen/$info"
    }
}