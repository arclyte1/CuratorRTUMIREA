package com.example.curatorrtumirea.presentation.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.curatorrtumirea.common.LocalNavigator
import com.example.curatorrtumirea.common.conditional

class BottomNavBarState {
    var currentItem by mutableStateOf<BottomNavItem>(BottomNavItem.Events)
    var isVisible by mutableStateOf(false)

    fun setupBottomBar(
        currentItem: BottomNavItem = BottomNavItem.Events,
        isVisible: Boolean = false
    ) {
        this.currentItem = currentItem
        this.isVisible = isVisible
    }
}

sealed class BottomNavItem(
    val icon: ImageVector,
    val route: String? = null
) {
    data object Events :
        BottomNavItem(icon = Icons.Default.Event, route = Destination.EventListScreen(false))

    data object Groups :
        BottomNavItem(icon = Icons.Default.Groups, route = Destination.GroupListScreen(false))

    data object Profile :
        BottomNavItem(icon = Icons.Default.Person, route = Destination.ProfileScreen())
}

@Composable
fun BottomNavBar(currentItem: BottomNavItem) {
    val navigator = LocalNavigator.current
    val navItems = listOf(BottomNavItem.Events, BottomNavItem.Groups, BottomNavItem.Profile)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Divider(modifier = Modifier.fillMaxWidth())
        BottomAppBar {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                navItems.forEach { item ->
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "icon",
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                            .padding(bottom = 4.dp)
                            .conditional(item != currentItem) {
                                this.alpha(0.4f)
                            }
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                item.route?.let { route ->
                                    navigator.tryNavigateTo(
                                        route = route,
                                        clearBackStack = true,
                                    )
                                }
                            }
                    )
                }
            }
        }
    }
}