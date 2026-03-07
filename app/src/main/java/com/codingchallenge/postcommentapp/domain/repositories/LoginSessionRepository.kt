package com.codingchallenge.postcommentapp.domain.repositories

import kotlinx.coroutines.flow.Flow

interface LoginSessionRepository {
    val isLoggedIn: Flow<Boolean>
    suspend fun storeLoginStatus(isLoggedIn: Boolean)
    suspend fun logout()
}
