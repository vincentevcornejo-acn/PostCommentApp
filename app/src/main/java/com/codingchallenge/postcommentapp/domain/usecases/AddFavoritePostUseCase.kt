package com.codingchallenge.postcommentapp.domain.usecases

import com.codingchallenge.postcommentapp.domain.model.Post
import com.codingchallenge.postcommentapp.domain.repositories.PostCommentRepository
import javax.inject.Inject

class AddFavoritePostUseCase @Inject constructor(
    private val repository: PostCommentRepository
) {
    suspend operator fun invoke(post: Post) = repository.addFavoritePost(post)
}