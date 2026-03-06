package com.codingchallenge.postcommentapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingchallenge.postcommentapp.data.local.entiy.PostEntity
import com.codingchallenge.postcommentapp.domain.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostCommentDao {

    @Query("SELECT * FROM favorite_posts")
    fun getFavoritePosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoritePost(postEntity: PostEntity)

    @Query("DELETE FROM favorite_posts WHERE id = :id")
    suspend fun deleteFavoritePost(id: Int)
}
