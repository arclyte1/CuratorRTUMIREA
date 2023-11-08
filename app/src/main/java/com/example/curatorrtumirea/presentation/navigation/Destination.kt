package com.example.curatorrtumirea.presentation.navigation

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