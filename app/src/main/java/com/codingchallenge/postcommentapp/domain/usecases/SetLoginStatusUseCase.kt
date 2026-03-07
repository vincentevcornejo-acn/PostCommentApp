package com.codingchallenge.postcommentapp.domain.usecases

import com.codingchallenge.postcommentapp.domain.repositories.LoginSessionRepository
import javax.inject.Inject

class SetLoginStatusUseCase @Inject constructor(
    private val repository: LoginSessionRepository
) {
    suspend operator fun invoke(isLoggedIn: Boolean) {
        repository.storeLoginStatus(isLoggedIn)
    }
}