package com.example.curatorrtumirea.presentation.screen.request_list.model

import androidx.annotation.StringRes
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.domain.model.Request
import com.example.curatorrtumirea.domain.model.RequestStatus

data class RequestListItem(
    val id: Long,
    val title: String,
    @StringRes val status: Int,
    val description: String,
) {

    companion object {
        fun fromRequest(request: Request): RequestListItem {
            return RequestListItem(
                id = request.id,
                title = request.title,
                status = getRequestStatusStringRes(request.status),
                description = request.description
            )
        }

        private fun getRequestStatusStringRes(status: RequestStatus): Int {
            return when(status) {
                RequestStatus.OPEN -> R.string.request_open
                RequestStatus.REJECTED -> R.string.request_rejected
                RequestStatus.RESOLVED -> R.string.request_resolved
            }
        }
    }
}
