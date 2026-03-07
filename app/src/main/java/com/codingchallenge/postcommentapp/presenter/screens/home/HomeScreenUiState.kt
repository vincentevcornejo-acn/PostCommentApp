package com.codingchallenge.postcommentapp.presenter.screens.home

import com.codingchallenge.postcommentapp.domain.model.Post

data class HomeScreenUiState(
    val selectedTab: HomeTab = HomeTab.POSTS,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val posts: List<Post> = emptyList(),
    val favorites: List<Post> = emptyList(),
    val favoriteIds: Set<Int> = emptySet(),
)
