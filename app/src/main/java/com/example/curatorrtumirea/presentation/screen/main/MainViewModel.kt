package com.example.curatorrtumirea.presentation.screen.main

import androidx.lifecycle.ViewModel
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appNavigator: AppNavigator
) : ViewModel() {

    val navigationChannel = appNavigator.navigationChannel
}