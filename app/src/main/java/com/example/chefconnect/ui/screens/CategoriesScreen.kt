package com.example.chefconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefconnect.ui.viewmodel.MealUiState
import com.example.chefconnect.ui.viewmodel.MealViewModel
import com.example.chefconnect.ui.components.CategoryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    viewModel: MealViewModel = MealViewModel() // Sin Hilt
) {
    val categoriesState by viewModel.categoriesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ChefConnect") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
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
            when (categoriesState) {
                is MealUiState.Loading -> CircularProgressIndicator()
                is MealUiState.SuccessCategories -> {
                    val categories = (categoriesState as MealUiState.SuccessCategories).categories
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(categories) { category ->
                            CategoryCard(
                                category = category,
                                onClick = {
                                    navController.navigate("meals/${category.strCategory}")
                                }
                            )
                        }
                    }
                }
                is MealUiState.SuccessMeals -> {
                    // Este estado no debería aparecer aquí, pero lo manejamos
                    Text("No se esperaban comidas en esta pantalla")
                }
                is MealUiState.Error -> {
                    Text(
                        text = (categoriesState as MealUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}