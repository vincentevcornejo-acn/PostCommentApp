package com.codingchallenge.postcommentapp.presenter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codingchallenge.postcommentapp.presenter.screens.home.HomeScreen
import com.codingchallenge.postcommentapp.presenter.screens.home.HomeScreenViewModel

sealed class Screen(val route: String) {
    object Home : Screen(route = "Home")
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(route = Screen.Home.route){
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(viewModel = homeScreenViewModel)
        }
    }
}