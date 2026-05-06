package com.example.chefconnect.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chefconnect.ui.screens.*

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object MealsGrid : Screen("meals/{categoryName}") {
        @Suppress("unused")
        fun passCategory(categoryName: String) = "meals/$categoryName"
    }
    object MealDetail : Screen("meal/{mealId}") {
        fun passMealId(mealId: String) = "meal/$mealId"
    }
    object Search : Screen("search")
    object Favorites : Screen("favorites")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChefConnectNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Categories.route) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Search.route) },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Favorites.route) },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Categories.route,
            modifier = modifier.padding(innerPadding) // Usamos innerPadding aquí
        ) {
            composable(Screen.Categories.route) {
                CategoriesScreen(navController)
            }
            composable(
                route = Screen.MealsGrid.route,
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                // Temporal hasta crear MealsGridScreen
                Text("Pantalla de comidas para: $categoryName")
            }
            composable(
                route = Screen.MealDetail.route,
                arguments = listOf(navArgument("mealId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                // Temporal hasta crear MealDetailScreen
                Text("Detalle de comida ID: $mealId")
            }
            composable(Screen.Search.route) {
                Text("Pantalla de búsqueda")
            }
            composable(Screen.Favorites.route) {
                Text("Pantalla de favoritos")
            }
        }
    }
}