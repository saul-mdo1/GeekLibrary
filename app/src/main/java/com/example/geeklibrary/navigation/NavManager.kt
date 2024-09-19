package com.example.geeklibrary.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geeklibrary.model.Routes
import com.example.geeklibrary.screens.AddScreen
import com.example.geeklibrary.screens.DetailsScreen
import com.example.geeklibrary.screens.HomeScreen
import com.example.geeklibrary.screens.UpdateScreen
import com.example.geeklibrary.viewModel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavManager(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME.route) {
        composable(
            route = Routes.HOME.route,
            arguments = listOf(navArgument("tabIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 0
            HomeScreen(navController, viewModel, tabIndex)
        }

        composable(
            route = Routes.ADD_ELEMENT.route,
            arguments = listOf(
                navArgument("type") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            AddScreen(
                navController = navController,
                type = backStackEntry.arguments?.getInt("type"),
                vm = viewModel
            )
        }

        composable(
            route = Routes.UPDATE_ELEMENT.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("type") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            UpdateScreen(
                navController = navController,
                vm = viewModel,
                type = backStackEntry.arguments?.getInt("type"),
                elementId = backStackEntry.arguments?.getInt("id")
            )
        }

        composable(
            route = Routes.DETAILS.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("type") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            DetailsScreen(
                navController = navController,
                vm = viewModel,
                type = backStackEntry.arguments?.getInt("type"),
                elementId = backStackEntry.arguments?.getInt("id")
            )
        }
    }
}