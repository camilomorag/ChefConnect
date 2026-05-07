package com.example.chefconnect.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.chefconnect.ui.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    mealId: String,
    onBackClick: () -> Unit,
    viewModel: MealViewModel = viewModel()
) {
    val mealDetailState by viewModel.mealDetailState.collectAsState()

    LaunchedEffect(mealId) {
        viewModel.getMealDetails(mealId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        when (mealDetailState) {
            null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFF4CAF50))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading recipe...", color = Color.Gray)
                    }
                }
            }
            else -> {
                val meal = mealDetailState!!
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Hero Image Section
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(380.dp)
                            ) {
                                AsyncImage(
                                    model = meal.strMealThumb,
                                    contentDescription = meal.strMeal,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                // Gradient overlay
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.6f)
                                                ),
                                                startY = 0.5f,
                                                endY = 1f
                                            )
                                        )
                                )
                            }
                        }

                        // Content Section
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                            ) {
                                Spacer(modifier = Modifier.height(8.dp))

                                // Title and Rating
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = meal.strMeal,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A1A1A),
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFFFB300).copy(alpha = 0.1f)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Star,
                                                contentDescription = "Rating",
                                                tint = Color(0xFFFFB300),
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "4.9",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color(0xFFFFB300)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Info Chips (Time, Temp, Servings)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    InfoChip(
                                        icon = Icons.Default.Timer,
                                        text = "30 min",
                                        color = Color(0xFF4CAF50)
                                    )
                                    InfoChip(
                                        icon = Icons.Default.Whatshot,
                                        text = "400°F",
                                        color = Color(0xFFF44336)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = Color(0xFFF5F5F5)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.People,
                                                contentDescription = "Servings",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "2 servings",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                // Nutritional Info Card
                                Card(
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF8F9FA)
                                    ),
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 20.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        NutritionalItem(value = "420", unit = "kcal", label = "Calories")
                                        NutritionalItem(value = "28g", unit = "", label = "Protein")
                                        NutritionalItem(value = "15g", unit = "", label = "Fat")
                                        NutritionalItem(value = "12g", unit = "", label = "Carbs")
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                // Category & Cuisine
                                if (!meal.strCategory.isNullOrEmpty() || !meal.strArea.isNullOrEmpty()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        if (!meal.strCategory.isNullOrEmpty()) {
                                            CategoryPill(
                                                text = meal.strCategory,
                                                color = Color(0xFF4CAF50)
                                            )
                                        }
                                        if (!meal.strArea.isNullOrEmpty()) {
                                            CategoryPill(
                                                text = meal.strArea,
                                                color = Color(0xFFFF9800)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(24.dp))
                                }

                                // Ingredients Section Title
                                Text(
                                    text = "Ingredients",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1A1A)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "3/4 oz - 2 servings",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }

                        // Ingredients List
                        val ingredientsList = meal.getIngredientsList()
                        items(ingredientsList) { (ingredient, measure) ->
                            IngredientItem(
                                name = ingredient,
                                measure = measure.ifEmpty { "To taste" }
                            )
                        }

                        // Instructions Section
                        item {
                            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = "Instructions",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1A1A)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Card(
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF8F9FA)
                                    )
                                ) {
                                    Text(
                                        text = meal.strInstructions?.replace("\n", "\n\n")
                                            ?: "No instructions available",
                                        style = MaterialTheme.typography.bodyMedium,
                                        lineHeight = 24.sp,
                                        modifier = Modifier.padding(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(100.dp))
                            }
                        }
                    }

                    // Bottom Button
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .height(56.dp)
                            .align(Alignment.BottomCenter),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1A1A1A)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp
                        )
                    ) {
                        Text(
                            "Start Cooking",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

@Composable
fun NutritionalItem(value: String, unit: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF1A1A1A)
            )
            if (unit.isNotEmpty()) {
                Text(
                    text = unit,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CategoryPill(text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                Icons.Default.Folder,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

@Composable
fun IngredientItem(name: String, measure: String) {
    var checked by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (checked) Color(0xFFE8F5E9) else Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { checked = !checked }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50),
                    uncheckedColor = Color(0xFFBDBDBD)
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = if (checked) FontWeight.Medium else FontWeight.Normal,
                    fontSize = 15.sp,
                    color = if (checked) Color(0xFF4CAF50) else Color(0xFF333333)
                )
                Text(
                    text = measure,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}