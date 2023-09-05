package com.example.uninsubriasurvive.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object Home :Screen("main", Icons.Outlined.Home)
    object Exams :Screen("exams" , Icons.Outlined.List)
    object Booklet :Screen("booklet", Icons.Outlined.Menu)
    object Pavilion :Screen("pavilion", Icons.Outlined.Home)
}
