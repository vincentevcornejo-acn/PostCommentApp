package com.codingchallenge.postcommentapp.domain.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
