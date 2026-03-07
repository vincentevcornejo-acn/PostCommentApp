package com.codingchallenge.postcommentapp.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.codingchallenge.postcommentapp.domain.repositories.LoginSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginSessionRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LoginSessionRepository {

    private object Keys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    override val isLoggedIn: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.IS_LOGGED_IN] ?: false }
        .catch { emit(false) }


    override suspend fun storeLoginStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.IS_LOGGED_IN] = isLoggedIn
        }
    }

    override suspend fun logout() {
        dataStore.edit { it.clear() }
    }
}