// data/network/MealApiService.kt
package com.example.chefconnect.data.network

import com.example.chefconnect.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    // Obtener todas las categorías
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    // Obtener comidas por categoría (Seafood, Beef, etc.)
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealResponse

    // Buscar comidas por nombre
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse

    // Obtener detalle completo de una comida por ID
    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): MealResponse
}