// data/repository/MealRepository.kt
package com.example.chefconnect.data.repository

import com.example.chefconnect.data.network.RetrofitClient
import com.example.chefconnect.data.model.*

class MealRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getCategories(): CategoryResponse? {
        return try {
            apiService.getCategories()
        } catch (e: Exception) {
            null // O podrías lanzar una excepción personalizada
        }
    }

    suspend fun getMealsByCategory(category: String): MealResponse? {
        return try {
            apiService.getMealsByCategory(category)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun searchMeals(query: String): MealResponse? {
        return try {
            apiService.searchMeals(query)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMealDetails(mealId: String): MealResponse? {
        return try {
            apiService.getMealDetails(mealId)
        } catch (e: Exception) {
            null
        }
    }
}