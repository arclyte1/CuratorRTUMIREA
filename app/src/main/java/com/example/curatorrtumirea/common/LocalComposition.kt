package com.example.curatorrtumirea.common

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.BottomNavBarState
import com.example.curatorrtumirea.presentation.navigation.BottomNavItem

val LocalNavigator = staticCompositionLocalOf<AppNavigator> {
    error("No AppNavigator provided")
}

val LocalBottomNavBarState = staticCompositionLocalOf<BottomNavBarState>{
    error("No BottomNavBarState provided")
}