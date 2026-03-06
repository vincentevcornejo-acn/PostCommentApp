package com.codingchallenge.postcommentapp.domain.usecases

import com.codingchallenge.postcommentapp.domain.repositories.PostCommentRepository
import javax.inject.Inject

class GetFavoritePostsUseCase @Inject constructor(
    private val repository: PostCommentRepository
) {
    operator fun invoke() = repository.getFavoritePosts()
}