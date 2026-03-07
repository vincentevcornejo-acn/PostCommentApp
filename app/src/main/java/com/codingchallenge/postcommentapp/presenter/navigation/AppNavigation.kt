package com.codingchallenge.postcommentapp.presenter.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codingchallenge.postcommentapp.MainViewModel
import com.codingchallenge.postcommentapp.presenter.screens.home.HomeScreen
import com.codingchallenge.postcommentapp.presenter.screens.home.HomeScreenViewModel
import com.codingchallenge.postcommentapp.presenter.screens.login.LoginScreen
import com.codingchallenge.postcommentapp.presenter.screens.login.LoginScreenViewModel

sealed class Screen(val route: String) {

    data object Login : Screen(route = "Login")
    data object Home : Screen(route = "Home")
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsStateWithLifecycle()

    if (isLoggedIn == null) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(
        navController = navHostController,
        startDestination = if (isLoggedIn == true) Screen.Home.route else Screen.Login.route,
        modifier = modifier,
    ) {
        composable(route = Screen.Login.route) {
            val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
            LoginScreen(
                viewModel = loginScreenViewModel,
                onLoginSuccess = {
                    loginScreenViewModel.login(
                        onSuccess = {
                            navHostController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            )
        }

        composable(route = Screen.Home.route) {
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(viewModel = homeScreenViewModel)
        }
    }
}