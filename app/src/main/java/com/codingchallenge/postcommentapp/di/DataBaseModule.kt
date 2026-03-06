package com.codingchallenge.postcommentapp.di

import android.content.Context
import androidx.room.Room
import com.codingchallenge.postcommentapp.data.local.PostCommentDao
import com.codingchallenge.postcommentapp.data.local.PostCommentDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PostCommentDb =
        Room.databaseBuilder(context, PostCommentDb::class.java, name = "post_comment_db").build()

    @Provides
    fun providePostCommentDao(db: PostCommentDb): PostCommentDao = db.postCommentDao()
}