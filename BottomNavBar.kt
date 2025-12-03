package com.example.snowtimerapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun BottomNavBar(
    currentRoute: String,
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItem("main", "홈", Icons.Filled.Home),
        BottomNavItem("search", "검색", Icons.Filled.Search),
        BottomNavItem("group", "그룹", Icons.Filled.Group),
        BottomNavItem("settings", "설정", Icons.Filled.Settings)
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items.forEach { item ->
            val iconColor =
                if (item.route == currentRoute) Color.Black else Color.Gray

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = iconColor,
                        modifier = Modifier.size(30.dp)
                    )
                },
                selected = false,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}