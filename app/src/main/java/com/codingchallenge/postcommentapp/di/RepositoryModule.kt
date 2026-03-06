package com.codingchallenge.postcommentapp.di

import com.codingchallenge.postcommentapp.data.repositories.PostCommentRepositoryImpl
import com.codingchallenge.postcommentapp.domain.repositories.PostCommentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostCommentRepository(
        impl: PostCommentRepositoryImpl
    ): PostCommentRepository

}
