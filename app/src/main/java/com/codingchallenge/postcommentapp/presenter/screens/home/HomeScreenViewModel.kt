package com.codingchallenge.postcommentapp.presenter.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingchallenge.postcommentapp.domain.model.Post
import com.codingchallenge.postcommentapp.domain.usecases.AddFavoritePostUseCase
import com.codingchallenge.postcommentapp.domain.usecases.DeleteFavoritePostUseCase
import com.codingchallenge.postcommentapp.domain.usecases.FetchPostsUseCase
import com.codingchallenge.postcommentapp.domain.usecases.GetFavoritePostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase,
    private val addFavoritePostUseCase: AddFavoritePostUseCase,
    private val deleteFavoritePostUseCase: DeleteFavoritePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    init {
        getFavoritePosts()
        fetchPosts()
    }

    fun selectTab(tab: HomeTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            fetchPostsUseCase()
                .onStart {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                    Log.d("HomeScreenViewModel --- start", "loading:: ${uiState.value.isLoading}")
                }.catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message ?: "Unknown error") }
                    Log.d("HomeScreenViewModel --- catch", "error:: ${uiState.value.errorMessage}")
                }.onCompletion {
                    _uiState.update { it.copy(isLoading = false) }
                    Log.d(
                        "HomeScreenViewModel --- completion",
                        "loading:: ${uiState.value.isLoading}"
                    )
                }.collect { posts ->
                    _uiState.update { it.copy(posts = posts) }
                    Log.d("HomeScreenViewModel --- collect", "size:: ${uiState.value.posts.size}")
                }
        }
    }

    fun getFavoritePosts(){
        viewModelScope.launch {
            getFavoritePostsUseCase().collect{ posts ->
                _uiState.update { it.copy(favorites = posts) }
            }
        }
    }
    fun onToggleFavorite(isFavorite: Boolean, post: Post) {
        viewModelScope.launch {
            if (isFavorite) deleteFavoritePostUseCase(post.id) else addFavoritePostUseCase(post)
        }
    }
}