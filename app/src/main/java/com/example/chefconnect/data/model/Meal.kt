package com.example.chefconnect.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories") val categories: List<Category>?
)

data class MealResponse(
    @SerializedName("meals") val meals: List<Meal>?
)

data class Category(
    @SerializedName("idCategory") val idCategory: String,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String
)

// Versión simplificada de Meal
data class Meal(
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String,
    @SerializedName("strCategory") val strCategory: String? = null,
    @SerializedName("strArea") val strArea: String? = null,
    @SerializedName("strInstructions") val strInstructions: String? = null,
    @SerializedName("strIngredient1") val strIngredient1: String? = null,
    @SerializedName("strIngredient2") val strIngredient2: String? = null,
    @SerializedName("strIngredient3") val strIngredient3: String? = null,
    @SerializedName("strIngredient4") val strIngredient4: String? = null,
    @SerializedName("strIngredient5") val strIngredient5: String? = null,
    @SerializedName("strMeasure1") val strMeasure1: String? = null,
    @SerializedName("strMeasure2") val strMeasure2: String? = null,
    @SerializedName("strMeasure3") val strMeasure3: String? = null,
    @SerializedName("strMeasure4") val strMeasure4: String? = null,
    @SerializedName("strMeasure5") val strMeasure5: String? = null,
    @SerializedName("strMeasure6") val strMeasure6: String? = null,
    @SerializedName("strMeasure7") val strMeasure7: String? = null,
    @SerializedName("strMeasure8") val strMeasure8: String? = null,
    @SerializedName("strMeasure9") val strMeasure9: String? = null,
    @SerializedName("strMeasure10") val strMeasure10: String? = null
) {
    // Obtener lista de ingredientes y medidas
    fun getIngredientsList(): List<Pair<String, String>> {
        val ingredients = mutableListOf<Pair<String, String>>()

        // Lista de todos los ingredientes
        val ingredientsList = listOf(
            strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5
        )

        val measuresList = listOf(
            strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
            strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10
        )

        for (i in ingredientsList.indices) {
            val ingredient = ingredientsList[i]
            val measure = if (i < measuresList.size) measuresList[i] else null

            if (!ingredient.isNullOrEmpty() && ingredient.isNotBlank()) {
                ingredients.add(Pair(ingredient.trim(), measure?.trim() ?: ""))
            }
        }

        return ingredients
    }
}