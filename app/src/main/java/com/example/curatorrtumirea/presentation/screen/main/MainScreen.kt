package com.example.curatorrtumirea.presentation.screen.main

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.navigation.NavHost
import com.example.curatorrtumirea.presentation.navigation.NavigationIntent
import com.example.curatorrtumirea.presentation.navigation.viewModelComposable
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationEffect
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationEvent
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationScreen
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationScreenState
import com.example.curatorrtumirea.presentation.screen.email_confirmation.EmailConfirmationViewModel
import com.example.curatorrtumirea.presentation.screen.event_list.EventListEffect
import com.example.curatorrtumirea.presentation.screen.event_list.EventListEvent
import com.example.curatorrtumirea.presentation.screen.event_list.EventListScreen
import com.example.curatorrtumirea.presentation.screen.event_list.EventListScreenState
import com.example.curatorrtumirea.presentation.screen.event_list.EventListViewModel
import com.example.curatorrtumirea.presentation.screen.login.LoginEffect
import com.example.curatorrtumirea.presentation.screen.login.LoginEvent
import com.example.curatorrtumirea.presentation.screen.login.LoginScreen
import com.example.curatorrtumirea.presentation.screen.login.LoginScreenState
import com.example.curatorrtumirea.presentation.screen.login.LoginViewModel
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController
    )

    CuratorRTUMIREATheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = Destination.EventListScreen
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
                        onEvent = onEvent
                    )
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
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}