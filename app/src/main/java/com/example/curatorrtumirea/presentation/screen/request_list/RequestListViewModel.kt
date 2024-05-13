package com.example.curatorrtumirea.presentation.screen.request_list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetRequestListUseCase
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.screen.request_list.model.RequestListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val getRequestListUseCase: GetRequestListUseCase
) : BaseViewModel<RequestListScreenState, RequestListEffect, RequestListEvent>(
    RequestListScreenState()
) {

    private fun refreshRequestList() {
        Log.d("REFRESHING REQUEST LIST", "REFRESHING REQUEST LIST")
        getRequestListUseCase().onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isLoadingRequests = false
                    ))
                    // TODO: show error message
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isLoadingRequests = true
                    ))
                }
                is Resource.Success -> {
                    setState(state.value.copy(
                        isLoadingRequests = false,
                        requests = resource.data.map { RequestListItem.fromRequest(it) }
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: RequestListEvent) {
        when(event) {
            RequestListEvent.RefreshRequestList -> {
                refreshRequestList()
            }

            RequestListEvent.CreateRequestClicked -> {
                appNavigator.tryNavigateTo(Destination.CreateRequestScreen())
            }
        }
    }
}