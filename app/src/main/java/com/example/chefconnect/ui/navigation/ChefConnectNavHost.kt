package com.example.chefconnect.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chefconnect.ui.screens.*

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object MealsGrid : Screen("meals/{categoryName}") {
        fun passCategory(categoryName: String) = "meals/$categoryName"
    }
    object MealDetail : Screen("mealDetail/{mealId}") {
        fun passMealId(mealId: String) = "mealDetail/$mealId"
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
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.Categories.route,
                    onClick = { navController.navigate(Screen.Categories.route) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4CAF50),
                        selectedTextColor = Color(0xFF4CAF50)
                    )
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.Search.route,
                    onClick = { navController.navigate(Screen.Search.route) },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4CAF50),
                        selectedTextColor = Color(0xFF4CAF50)
                    )
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.Favorites.route,
                    onClick = { navController.navigate(Screen.Favorites.route) },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4CAF50),
                        selectedTextColor = Color(0xFF4CAF50)
                    )
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Categories.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Categories.route) {
                CategoriesScreen(navController)
            }
            composable(
                route = Screen.MealsGrid.route,
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                MealsGridScreen(
                    navController = navController,
                    categoryName = categoryName
                )
            }
            composable(
                route = Screen.MealDetail.route,
                arguments = listOf(navArgument("mealId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                MealDetailScreen(
                    mealId = mealId,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(navController = navController)
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(navController = navController)
            }
        }
    }
}