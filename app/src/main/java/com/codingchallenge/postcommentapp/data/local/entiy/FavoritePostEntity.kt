package com.codingchallenge.postcommentapp.data.local.entiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingchallenge.postcommentapp.domain.model.Post

@Entity(tableName = "favorite_posts")
data class FavoritePostEntity(

    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    companion object {
        fun fromFavoritePost(post: Post): FavoritePostEntity = FavoritePostEntity(
            id = post.id,
            userId = post.userId,
            title = post.title,
            body = post.body
        )
    }
}

fun FavoritePostEntity.toPost() = Post(
    id = id,
    userId = userId,
    title = title,
    body = body,
)
