package com.codingchallenge.postcommentapp.domain.repositories

import com.codingchallenge.postcommentapp.domain.model.Post
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow

interface PostCommentRepository {

    // remote
    fun fetchPosts(): Flow<List<Post>>

    // database
    fun getFavoritePosts(): Flow<List<Post>>
    suspend fun addFavoritePost(post: Post)
    suspend fun deleteFavoritePost(id: Int)
}