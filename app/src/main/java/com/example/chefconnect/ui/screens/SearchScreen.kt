package com.example.chefconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chefconnect.ui.viewmodel.MealUiState
import com.example.chefconnect.ui.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: MealViewModel = MealViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchState by viewModel.searchState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Recipes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.searchMeals(it)
                    },
                    placeholder = { Text("Search recipes, ingredients...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            // Results
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (searchState) {
                    is MealUiState.Loading -> CircularProgressIndicator()
                    is MealUiState.SuccessMeals -> {
                        val meals = (searchState as MealUiState.SuccessMeals).meals
                        if (meals.isEmpty()) {
                            Text("No recipes found for '$searchQuery'")
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(meals) { meal ->
                                    Card(
                                        onClick = {
                                            navController.navigate("meal/${meal.idMeal}")
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        ) {
                                            AsyncImage(
                                                model = meal.strMealThumb,
                                                contentDescription = meal.strMeal,
                                                modifier = Modifier.size(80.dp)
                                            )
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Text(
                                                text = meal.strMeal,
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is MealUiState.SuccessCategories -> {
                        // Este caso no debería ocurrir en SearchScreen, pero lo manejamos
                        Text("Unexpected state: Categories loaded")
                    }
                    is MealUiState.Error -> {
                        Text(
                            text = (searchState as MealUiState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}