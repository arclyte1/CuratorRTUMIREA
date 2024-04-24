package com.example.curatorrtumirea.presentation.navigation

import android.util.Log

sealed class Destination(
    protected val route: String,
    vararg params: String
) {

    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object LoginScreen : NoArgumentsDestination("login")

    data object EmailConfirmationScreen : Destination("email_confirmation", "email") {
        const val EMAIL_KEY = "email"

        operator fun invoke(email: String): String = route.appendParams(
            EMAIL_KEY to email
        )
    }

    data object EventListScreen : Destination("event_list", "force_refresh", "group_filter_id") {
        const val FORCE_REFRESH = "force_refresh"
        const val GROUP_FILTER_ID = "group_filter_id"
        const val GROUP_FILTER_ID_NULL_VALUE = -1L

        operator fun invoke(
            forceRefresh: Boolean = true,
            groupFilterId: Long = GROUP_FILTER_ID_NULL_VALUE
        ): String = route.appendParams(
            FORCE_REFRESH to forceRefresh,
            GROUP_FILTER_ID to groupFilterId
        )
    }

    data object GroupListScreen : Destination("group_list", "force_refresh") {
        const val FORCE_REFRESH = "force_refresh"

        operator fun invoke(forceRefresh: Boolean = true): String = route.appendParams(
            FORCE_REFRESH to forceRefresh
        )
    }

    data object EventDetailsScreen : Destination("event_details", "event_id") {
        const val EVENT_ID = "event_id"

        operator fun invoke(eventId: Long): String = route.appendParams(
            EVENT_ID to eventId
        )
    }

    data object AttendancesScreen : Destination("attendances", "event_id") {
        const val EVENT_ID = "event_id"

        operator fun invoke(eventId: Long): String = route.appendParams(
            EVENT_ID to eventId
        )
    }

    data object GroupDetailsScreen : Destination("group_details", "group_id") {
        const val GROUP_ID = "group_id"

        operator fun invoke(eventId: Long): String = route.appendParams(
            GROUP_ID to eventId
        )
    }

    data object ProfileScreen : NoArgumentsDestination("profile")
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}