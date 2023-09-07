package com.example.uninsubriasurvive.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.uninsubriasurvive.R

sealed class Screen(val route: String, val icon: Int) {
    object Home :Screen("main", R.drawable.outline_home_24)
    object Exams :Screen("exams" , R.drawable.outline_format_list_bulleted_24)
    object Booklet :Screen("booklet",  R.drawable.outline_book_24)
    object Pavilion :Screen("pavilion", R.drawable.outline_maps_home_work_24)
}
