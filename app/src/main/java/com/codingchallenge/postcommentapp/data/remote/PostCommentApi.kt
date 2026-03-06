package com.codingchallenge.postcommentapp.data.remote

import com.codingchallenge.postcommentapp.data.remote.dto.PostDto
import retrofit2.http.GET

interface PostCommentApi {

    @GET("/posts")
    suspend fun fetchPosts(): List<PostDto>

}