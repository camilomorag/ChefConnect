package com.example.chefconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.chefconnect.ui.navigation.ChefConnectNavHost
import com.example.chefconnect.ui.navigation.Screen
import com.example.chefconnect.ui.theme.ChefConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChefConnectTheme {
                val navController = rememberNavController()

                // Handle Deep Link
                LaunchedEffect(Unit) {
                    intent?.data?.let { uri ->
                        if (uri.scheme == "chefconnect" && uri.host == "details") {
                            val mealId = uri.lastPathSegment ?: return@let
                            navController.navigate(Screen.MealDetail.passMealId(mealId))
                        }
                    }
                }

                ChefConnectNavHost()
            }
        }
    }
}