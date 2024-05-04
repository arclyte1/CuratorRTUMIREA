package com.example.curatorrtumirea.presentation.screen.request_list

import com.example.curatorrtumirea.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(

) : BaseViewModel<RequestListScreenState, RequestsListEffect, RequestsListEvent>(
    RequestListScreenState()
) {

    override fun onEvent(event: RequestsListEvent) {
        TODO("Not yet implemented")
    }
}