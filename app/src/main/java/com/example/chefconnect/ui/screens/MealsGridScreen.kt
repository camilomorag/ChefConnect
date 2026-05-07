package com.example.chefconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chefconnect.ui.viewmodel.MealUiState
import com.example.chefconnect.ui.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsGridScreen(
    navController: NavController,
    categoryName: String,
    viewModel: MealViewModel = MealViewModel()
) {
    val mealsState by viewModel.mealsState.collectAsState()

    LaunchedEffect(categoryName) {
        viewModel.getMealsByCategory(categoryName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoryName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (mealsState) {
                is MealUiState.Loading -> CircularProgressIndicator(color = Color(0xFF4CAF50))
                is MealUiState.SuccessMeals -> {
                    val meals = (mealsState as MealUiState.SuccessMeals).meals
                    if (meals.isEmpty()) {
                        Text(
                            text = "No recipes found in $categoryName",
                            color = Color.Gray
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(meals) { meal ->
                                Card(
                                    onClick = {
                                        val mealId = meal.idMeal
                                        if (mealId.isNotEmpty()) {
                                            navController.navigate("mealDetail/$mealId")
                                        }
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        AsyncImage(
                                            model = meal.strMealThumb,
                                            contentDescription = meal.strMeal,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(140.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Text(
                                            text = meal.strMeal,
                                            modifier = Modifier.padding(12.dp),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is MealUiState.Error -> {
                    Text(
                        text = (mealsState as MealUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }
}