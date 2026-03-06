package com.codingchallenge.postcommentapp.data.repositories

import com.codingchallenge.postcommentapp.data.local.PostCommentDao
import com.codingchallenge.postcommentapp.data.local.entiy.PostEntity
import com.codingchallenge.postcommentapp.data.local.entiy.toPost
import com.codingchallenge.postcommentapp.data.remote.PostCommentApi
import com.codingchallenge.postcommentapp.data.remote.dto.toDomainPosts
import com.codingchallenge.postcommentapp.domain.model.Post
import com.codingchallenge.postcommentapp.domain.repositories.PostCommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostCommentRepositoryImpl @Inject constructor(
    private val api: PostCommentApi,
    private val dao: PostCommentDao
) : PostCommentRepository {

    // remote
    override fun fetchPosts(): Flow<List<Post>> = flow {
        val posts = api.fetchPosts().toDomainPosts()
        emit(posts)
    }

    // database
    override fun getFavoritePosts(): Flow<List<Post>> =
        dao.getFavoritePosts().map { list -> list.map { it.toPost() } }

    override suspend fun addFavoritePost(post: Post) {
        dao.addFavoritePost(PostEntity.fromPost(post))
    }

    override suspend fun deleteFavoritePost(id: Int) {
        dao.deleteFavoritePost(id)
    }
}