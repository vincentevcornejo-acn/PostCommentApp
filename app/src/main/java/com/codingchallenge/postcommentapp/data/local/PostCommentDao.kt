package com.codingchallenge.postcommentapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingchallenge.postcommentapp.data.local.entiy.FavoritePostEntity
import com.codingchallenge.postcommentapp.data.local.entiy.PostEntity
import com.codingchallenge.postcommentapp.domain.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostCommentDao {


    // Favorite queries
    @Query("SELECT * FROM favorite_posts")
    fun getFavoritePosts(): Flow<List<FavoritePostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoritePost(favoritePostEntity: FavoritePostEntity)

    @Query("DELETE FROM favorite_posts WHERE id = :id")
    suspend fun deleteFavoritePost(id: Int)

    // All posts queries
    @Query("SELECT * FROM all_posts") // Assuming your Entity has this tableName
    fun getAllCachedPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("DELETE FROM all_posts")
    suspend fun clearCache()


}
