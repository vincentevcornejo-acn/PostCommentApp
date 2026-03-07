package com.codingchallenge.postcommentapp.presenter.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    onLoginSuccess: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                isError = uiState.emailError != null,
                supportingText = {
                    uiState.emailError?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )
            Spacer(Modifier.size(4.dp))
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                isError = uiState.passwordError != null,
                supportingText = {
                    uiState.passwordError?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(Modifier.size(8.dp))
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .defaultMinSize(minWidth = OutlinedTextFieldDefaults.MinWidth),
                enabled = uiState.isSubmitEnabled
            ) {
                Text(text = "Sign in")
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview(){
    MaterialTheme {
        LoginScreen(hiltViewModel())
    }
}