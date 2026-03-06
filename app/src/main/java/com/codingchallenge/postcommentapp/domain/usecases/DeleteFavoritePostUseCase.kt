package com.codingchallenge.postcommentapp.domain.usecases

import com.codingchallenge.postcommentapp.domain.model.Post
import com.codingchallenge.postcommentapp.domain.repositories.PostCommentRepository
import javax.inject.Inject

class DeleteFavoritePostUseCase @Inject constructor(
    private val repository: PostCommentRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteFavoritePost(id)
}