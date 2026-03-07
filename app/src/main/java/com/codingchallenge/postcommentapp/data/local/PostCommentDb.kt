package com.codingchallenge.postcommentapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingchallenge.postcommentapp.data.local.entiy.FavoritePostEntity
import com.codingchallenge.postcommentapp.data.local.entiy.PostEntity

@Database(
    entities = [
        PostEntity::class,
        FavoritePostEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PostCommentDb : RoomDatabase() {
    abstract fun postCommentDao(): PostCommentDao
}

