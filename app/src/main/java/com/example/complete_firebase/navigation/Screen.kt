package com.example.complete_firebase.navigation

sealed class Screen( val screenName: String) {
    object LoginScreen : Screen("login_screen")
    object MainScreen : Screen("main_screen")

    fun getArg(vararg args: String) : String {
        val output = buildString {
            append(screenName)
            args.forEach { item ->
                append("/$item")
            }
        }
        return output
    }
}