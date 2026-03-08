package com.codingchallenge.postcommentapp.data.repositories

import com.codingchallenge.postcommentapp.data.local.PostCommentDao
import com.codingchallenge.postcommentapp.data.local.entiy.FavoritePostEntity
import com.codingchallenge.postcommentapp.data.local.entiy.PostEntity
import com.codingchallenge.postcommentapp.data.local.entiy.toPost
import com.codingchallenge.postcommentapp.data.remote.PostCommentApi
import com.codingchallenge.postcommentapp.data.remote.dto.toDomainPosts
import com.codingchallenge.postcommentapp.domain.model.Post
import com.codingchallenge.postcommentapp.domain.repositories.PostCommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostCommentRepositoryImpl @Inject constructor(
    private val api: PostCommentApi,
    private val dao: PostCommentDao
) : PostCommentRepository {

    // remote
    override fun fetchPosts(): Flow<List<Post>> = flow {
        val cachedPosts = dao.getAllCachedPosts().firstOrNull() ?: emptyList()

        if (cachedPosts.isNotEmpty()) {
            emit(cachedPosts.map { it.toPost() })
        }

        try {
            val remotePosts = api.fetchPosts().toDomainPosts()
            val entities = remotePosts.map { PostEntity.fromPost(it) }
            dao.clearCache()
            dao.insertPosts(entities)
        } catch (e: Exception) {
            // if the remote api fails, emit the cached posts
        }
        emitAll(dao.getAllCachedPosts().map { list -> list.map { it.toPost() } })
    }

    // database
    override fun getFavoritePosts(): Flow<List<Post>> =
        dao.getFavoritePosts().map { list -> list.map { it.toPost() } }

    override suspend fun addFavoritePost(post: Post) {
        dao.addFavoritePost(FavoritePostEntity.fromFavoritePost(post))
    }

    override suspend fun deleteFavoritePost(id: Int) {
        dao.deleteFavoritePost(id)
    }
}