// data/model/Meal.kt
package com.example.chefconnect.data.model

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals") val meals: List<Meal>?
)

data class Meal(
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String,
    @SerializedName("strCategory") val strCategory: String? = null,
    @SerializedName("strArea") val strArea: String? = null,
    @SerializedName("strInstructions") val strInstructions: String? = null,
    @SerializedName("strIngredient1") val strIngredient1: String? = null,
    // ... (Agrega hasta strIngredient20 para detalles completos)
    @SerializedName("strMeasure1") val strMeasure1: String? = null
    // ... (Agrega hasta strMeasure20)
)

// Clase separada para las categorías, ya que la API tiene un endpoint diferente
data class CategoryResponse(
    @SerializedName("categories") val categories: List<Category>?
)

data class Category(
    @SerializedName("idCategory") val idCategory: String,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String
)