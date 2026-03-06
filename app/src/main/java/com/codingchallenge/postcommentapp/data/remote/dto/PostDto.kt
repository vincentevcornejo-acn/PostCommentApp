package com.codingchallenge.postcommentapp.data.remote.dto

import com.codingchallenge.postcommentapp.domain.model.Post

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

fun PostDto.toDomainPost(): Post {
    return Post(
        userId = userId,
        id = id,
        title = title,
        body = body
    )
}

fun List<PostDto>.toDomainPosts(): List<Post> {
    return map { it.toDomainPost() }
}