package com.codingchallenge.postcommentapp.presenter.screens.login

import android.util.Patterns
import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingchallenge.postcommentapp.domain.usecases.SetLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val setLoginStatusUseCase: SetLoginStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _uiState.update {
            val isEmailValid = isEmailValid(newEmail)
            val isPasswordValid = isPasswordValid(it.password)

            it.copy(
                email = newEmail,
                emailError = if (newEmail.isEmpty() || isEmailValid) null else "Invalid email address",
                isSubmitEnabled = isEmailValid && isPasswordValid
            )
        }
    }
    fun onPasswordChanged(newPassword: String) {
        _uiState.update {
            val isEmailValid = isEmailValid(it.email)
            val isPasswordValid = isPasswordValid(newPassword)

            it.copy(
                password = newPassword,
                passwordError = if (newPassword.isEmpty() || isPasswordValid) null else "Invalid password",
                isSubmitEnabled = isEmailValid && isPasswordValid
            )
        }
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            setLoginStatusUseCase(true) // Persistent Cache
            onSuccess()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length in 8..15
    }
}