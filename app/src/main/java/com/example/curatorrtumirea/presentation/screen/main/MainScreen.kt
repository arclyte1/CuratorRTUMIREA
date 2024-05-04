package com.example.curatorrtumirea.presentation.screen.main

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.common.LocalNavigator
import com.example.curatorrtumirea.presentation.navigation.BottomNavBar
import com.example.curatorrtumirea.presentation.navigation.BottomNavBarState
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.navigation.NavHost
import com.example.curatorrtumirea.presentation.navigation.NavigationIntent
import com.example.curatorrtumirea.presentation.navigation.viewModelComposable
import com.example.curatorrtumirea.presentation.screen.attendances.AttendancesEffect
import com.example.curatorrtumirea.presentation.screen.attendances.AttendancesEvent
import com.example.curatorrtumirea.presentation.screen.attendances.AttendancesScreen
import com.example.curatorrtumirea.presentation.screen.attendances.AttendancesScreenState
import com.example.curatorrtumirea.presentation.screen.attendances.AttendancesViewModel
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationEffect
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationEvent
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationScreen
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationScreenState
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationViewModel
import com.example.curatorrtumirea.presentation.screen.event_details.EventDetailsEffect
import com.example.curatorrtumirea.presentation.screen.event_details.EventDetailsEvent
import com.example.curatorrtumirea.presentation.screen.event_details.EventDetailsScreen
import com.example.curatorrtumirea.presentation.screen.event_details.EventDetailsScreenState
import com.example.curatorrtumirea.presentation.screen.event_details.EventDetailsViewModel
import com.example.curatorrtumirea.presentation.screen.event_list.EventListEffect
import com.example.curatorrtumirea.presentation.screen.event_list.EventListEvent
import com.example.curatorrtumirea.presentation.screen.event_list.EventListScreen
import com.example.curatorrtumirea.presentation.screen.event_list.EventListScreenState
import com.example.curatorrtumirea.presentation.screen.event_list.EventListViewModel
import com.example.curatorrtumirea.presentation.screen.group_details.GroupDetailsEffect
import com.example.curatorrtumirea.presentation.screen.group_details.GroupDetailsEvent
import com.example.curatorrtumirea.presentation.screen.group_details.GroupDetailsScreen
import com.example.curatorrtumirea.presentation.screen.group_details.GroupDetailsScreenState
import com.example.curatorrtumirea.presentation.screen.group_details.GroupDetailsViewModel
import com.example.curatorrtumirea.presentation.screen.group_list.GroupListEffect
import com.example.curatorrtumirea.presentation.screen.group_list.GroupListEvent
import com.example.curatorrtumirea.presentation.screen.group_list.GroupListScreen
import com.example.curatorrtumirea.presentation.screen.group_list.GroupListScreenState
import com.example.curatorrtumirea.presentation.screen.group_list.GroupListViewModel
import com.example.curatorrtumirea.presentation.screen.login.LoginEffect
import com.example.curatorrtumirea.presentation.screen.login.LoginEvent
import com.example.curatorrtumirea.presentation.screen.login.LoginScreen
import com.example.curatorrtumirea.presentation.screen.login.LoginScreenState
import com.example.curatorrtumirea.presentation.screen.login.LoginViewModel
import com.example.curatorrtumirea.presentation.screen.profile.ProfileEffect
import com.example.curatorrtumirea.presentation.screen.profile.ProfileEvent
import com.example.curatorrtumirea.presentation.screen.profile.ProfileScreen
import com.example.curatorrtumirea.presentation.screen.profile.ProfileScreenState
import com.example.curatorrtumirea.presentation.screen.profile.ProfileViewModel
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val bottomNavBarState = remember { BottomNavBarState() }

    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController
    )

//    LaunchedEffect(Unit) {
//        mainViewModel.appNavigator.tryNavigateTo(
//            route = Destination.ProfileScreen(),
//            popUpToRoute = Destination.LoginScreen.fullRoute,
//            inclusive = true
//        )
//    }

    CuratorRTUMIREATheme {
        CompositionLocalProvider(
            LocalNavigator provides mainViewModel.appNavigator,
            LocalBottomNavBarState provides bottomNavBarState
        ) {
            Scaffold(
                bottomBar = {
                    if (bottomNavBarState.isVisible) {
                        BottomNavBar(currentItem = bottomNavBarState.currentItem)
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Destination.LoginScreen,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    viewModelComposable<LoginViewModel, LoginScreenState, LoginEffect, LoginEvent>(
                        destination = Destination.LoginScreen
                    ) { state, effect, onEvent ->
                        LoginScreen(
                            screenState = state,
                            effect = effect,
                            onEvent = onEvent
                        )
                    }
                    viewModelComposable<EmailConfirmationViewModel, EmailConfirmationScreenState, EmailConfirmationEffect, EmailConfirmationEvent>(
                        destination = Destination.EmailConfirmationScreen
                    ) { state, effect, onEvent ->
                        EmailConfirmationScreen(
                            screenState = state,
                            effect = effect,
                            onEvent = onEvent
                        )
                    }
                    viewModelComposable<EventListViewModel, EventListScreenState, EventListEffect, EventListEvent>(
                        destination = Destination.EventListScreen
                    ) { state, effect, onEvent ->
                        EventListScreen(
                            screenState = state,
                            effect = effect,
                            sendEvent = onEvent
                        )
                    }
                    viewModelComposable<GroupListViewModel, GroupListScreenState, GroupListEffect, GroupListEvent>(
                        destination = Destination.GroupListScreen
                    ) { state, effect, onEvent ->
                        GroupListScreen(
                            screenState = state,
                            effect = effect,
                            onEvent = onEvent
                        )
                    }
                    viewModelComposable<EventDetailsViewModel, EventDetailsScreenState, EventDetailsEffect, EventDetailsEvent>(
                        destination = Destination.EventDetailsScreen
                    ) { state, effect, onEvent ->
                        EventDetailsScreen(
                            screenState = state,
                            sendEvent = onEvent,
                            effects = effect
                        )
                    }
                    viewModelComposable<AttendancesViewModel, AttendancesScreenState, AttendancesEffect, AttendancesEvent>(
                        destination = Destination.AttendancesScreen
                    ) { state, effect, onEvent ->
                        AttendancesScreen(
                            screenState = state,
                            effects = effect,
                            sendEvent = onEvent
                        )
                    }
                    viewModelComposable<GroupDetailsViewModel, GroupDetailsScreenState, GroupDetailsEffect, GroupDetailsEvent>(
                        destination = Destination.GroupDetailsScreen
                    ) { state, effect, onEvent ->
                        GroupDetailsScreen(
                            screenState = state,
                            effects = effect,
                            sendEvent = onEvent
                        )
                    }
                    viewModelComposable<ProfileViewModel, ProfileScreenState, ProfileEffect, ProfileEvent>(
                        destination = Destination.ProfileScreen
                    ) { state, effect, onEvent ->
                        ProfileScreen(
                            state = state,
                            sendEvent = onEvent,
                            effects = effect
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> {
                    if (navHostController.currentBackStackEntry?.destination?.route?.substringBefore(
                            "/"
                        ) != intent.route.substringBefore("/")
                    ) {
                        navHostController.navigate(intent.route) {
                            launchSingleTop = intent.isSingleTop
                            intent.popUpToRoute?.let { popUpToRoute ->
                                popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                            }
                            if (intent.clearBackStack) {
                                popUpTo(navHostController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}