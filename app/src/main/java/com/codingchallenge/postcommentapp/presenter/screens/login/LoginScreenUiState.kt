package com.codingchallenge.postcommentapp.presenter.screens.login

data class LoginScreenUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isSubmitEnabled: Boolean = false,
)
