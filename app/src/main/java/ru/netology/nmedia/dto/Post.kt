package ru.netology.nmedia.dto

class PostVideo (
    val name : String = "",
    val videoURL : String = "",
    val viewsCount: Int = 0,

)
//--------------------------------------------------------------------------------------------------

data class Post(
    val id: Long = 0,
    val author: String = "",
    val published: String = "",
    val content: String = "",
    val likesCount : Int = 0,
    val likedByMe: Boolean = false,
    val sharedCount : Int = 0,
    val viewedCount : Int = 0,
    val vedeo : PostVideo? = null,
)
//--------------------------------------------------------------------------------------------------
