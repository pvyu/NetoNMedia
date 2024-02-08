package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likesCount : Int = 0,
    val likedByMe: Boolean = false,
    val sharedCount : Int = 0,
    val viewedCount : Int = 0
)