package com.codingchallenge.postcommentapp.presenter.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingchallenge.postcommentapp.domain.model.Post
import com.codingchallenge.postcommentapp.domain.usecases.AddFavoritePostUseCase
import com.codingchallenge.postcommentapp.domain.usecases.DeleteFavoritePostUseCase
import com.codingchallenge.postcommentapp.domain.usecases.FetchPostsUseCase
import com.codingchallenge.postcommentapp.domain.usecases.GetFavoritePostsUseCase
import com.codingchallenge.postcommentapp.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase,
    private val addFavoritePostUseCase: AddFavoritePostUseCase,
    private val deleteFavoritePostUseCase: DeleteFavoritePostUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFavoritePosts()
        fetchPosts()
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun selectTab(tab: HomeTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            fetchPostsUseCase()
                .onStart {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                }.catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Unknown error") }
                }.collect { posts ->
                    _uiState.update { it.copy(isLoading = false, posts = posts) }
                }
        }
    }

    fun getFavoritePosts() {
        viewModelScope.launch {
            getFavoritePostsUseCase()
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message ?: "Unknown error") }
                }
                .collect { favoritePosts ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            favorites = favoritePosts,
                            favoriteIds = favoritePosts.map { it.id }.toSet()
                        )
                    }
                }
        }
    }

    fun onToggleFavorite(post: Post) {
        viewModelScope.launch {
            val isFavorite = _uiState.value.favoriteIds.contains(post.id)
            try {
                if (isFavorite) {
                    deleteFavoritePostUseCase(post.id)
                } else {
                    addFavoritePostUseCase(post)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Unknown error") }
            }
        }
    }
}