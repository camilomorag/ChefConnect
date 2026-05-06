// ui/viewmodel/MealUiState.kt
package com.example.chefconnect.ui.viewmodel

import com.example.chefconnect.data.model.Meal
import com.example.chefconnect.data.model.Category

sealed class MealUiState {
    object Loading : MealUiState()
    data class SuccessCategories(val categories: List<Category>) : MealUiState()
    data class SuccessMeals(val meals: List<Meal>) : MealUiState()
    data class Error(val message: String) : MealUiState()
}