package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likesCount : Int = 0,
    var likedByMe: Boolean = false,
    var sharedCount : Int = 0,
    var viewedCount : Int = 0
)