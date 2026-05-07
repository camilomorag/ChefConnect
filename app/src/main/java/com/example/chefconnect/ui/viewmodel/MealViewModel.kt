package com.example.chefconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefconnect.data.model.Meal
import com.example.chefconnect.data.model.Category
import com.example.chefconnect.data.repository.MealRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {
    private val repository = MealRepository()

    // Estados MutableStateFlow
    private val _categoriesState = MutableStateFlow<MealUiState>(MealUiState.Loading)
    val categoriesState: StateFlow<MealUiState> = _categoriesState.asStateFlow()

    private val _mealsState = MutableStateFlow<MealUiState>(MealUiState.Loading)
    val mealsState: StateFlow<MealUiState> = _mealsState.asStateFlow()

    private val _searchState = MutableStateFlow<MealUiState>(MealUiState.Loading)
    val searchState: StateFlow<MealUiState> = _searchState.asStateFlow()

    private val _mealDetailState = MutableStateFlow<Meal?>(null)
    val mealDetailState: StateFlow<Meal?> = _mealDetailState.asStateFlow()

    private var searchJob: Job? = null

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            _categoriesState.value = MealUiState.Loading
            try {
                val response = repository.getCategories()
                if (response != null && !response.categories.isNullOrEmpty()) {
                    _categoriesState.value = MealUiState.SuccessCategories(response.categories)
                } else {
                    _categoriesState.value = MealUiState.Error("No categories found")
                }
            } catch (e: Exception) {
                _categoriesState.value = MealUiState.Error("Network error: ${e.message}")
            }
        }
    }

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {
            _mealsState.value = MealUiState.Loading
            try {
                val response = repository.getMealsByCategory(category)
                if (response != null && !response.meals.isNullOrEmpty()) {
                    _mealsState.value = MealUiState.SuccessMeals(response.meals)
                } else {
                    _mealsState.value = MealUiState.Error("No meals found for category: $category")
                }
            } catch (e: Exception) {
                _mealsState.value = MealUiState.Error("Network error: ${e.message}")
            }
        }
    }

    fun searchMeals(query: String) {
        if (query.length < 2) {
            _searchState.value = MealUiState.SuccessMeals(emptyList())
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            _searchState.value = MealUiState.Loading
            try {
                val response = repository.searchMeals(query)
                if (response != null && !response.meals.isNullOrEmpty()) {
                    _searchState.value = MealUiState.SuccessMeals(response.meals)
                } else {
                    _searchState.value = MealUiState.SuccessMeals(emptyList())
                }
            } catch (e: Exception) {
                _searchState.value = MealUiState.Error("Search failed: ${e.message}")
            }
        }
    }

    fun getMealDetails(mealId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMealDetails(mealId)
                if (response != null && !response.meals.isNullOrEmpty()) {
                    _mealDetailState.value = response.meals.first()
                } else {
                    _mealDetailState.value = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _mealDetailState.value = null
            }
        }
    }
}