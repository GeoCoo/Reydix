package com.android.navigation.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.navigation.api.Screen
import com.android.navigation.routers.detailsScreenRouter
import com.android.navigation.routers.mainScreenRouter

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {

        this.mainScreenRouter(navController)
        this.detailsScreenRouter(navController)

    }
}

