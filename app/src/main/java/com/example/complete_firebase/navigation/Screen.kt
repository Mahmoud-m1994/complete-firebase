package com.example.complete_firebase.navigation

sealed class Screen( val screenName: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object MainScreen : Screen("main_screen")
    object ForgotPasswordScreen : Screen("forgot_password_screen")

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