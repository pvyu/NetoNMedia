package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var stepForSharedCounter : Int = 1

    //todo: post : MutableList<Post> = mutableListOf<Post> ? постоянные копии !!!
    private var posts : List<Post> = listOf<Post> (
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "04 февракля в 21:43",
            likesCount = 0,
            likedByMe = false,
            sharedCount = 0,
            viewedCount = 0,
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это второй!",
            published = "08 февракля в 21:43",
            likesCount = 0,
            likedByMe = false,
            sharedCount = 0,
            viewedCount = 0,
        ),
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это третий!",
            published = "08 февракля в 21:43",
            likesCount = 0,
            likedByMe = false,
            sharedCount = 0,
            viewedCount = 0,
        ),
        Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это четвёртый!",
            published = "08 февракля в 21:43",
            likesCount = 0,
            likedByMe = false,
            sharedCount = 0,
            viewedCount = 0,
        ),
        Post(
            id = 5,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это пятый!",
            published = "08 февракля в 21:43",
            likesCount = 0,
            likedByMe = false,
            sharedCount = 0,
            viewedCount = 0,
        ),
        Post(
            id = 6,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это шестой!",
            published = "08 февракля в 21:43",
            likesCount = 0,
            likedByMe = false,
            sharedCount = 0,
            viewedCount = 0,
        ),
        Post(
            id = 7,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это седьмой!",
            published = "08 февракля в 21:43",
            likesCount = 0,
            likedByMe = false,
            sharedCount = 0,
            viewedCount = 0,
        ),
    )

    private val data : MutableLiveData<List<Post>> = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id : Long) {
        posts = posts.map {
            if(it.id != id) {
                it }
            else {
                it.copy(likedByMe = !it.likedByMe,
                        likesCount = if (!it.likedByMe) { it.likesCount + 1 } else { it.likesCount - 1 } )
            }
        }
        data.value = posts
    }

    override fun shareById(id : Long) {
        posts = posts.map {
            if(it.id != id) {
                it }
            else {
                it.copy(sharedCount = it.sharedCount + stepForSharedCounter)
            }
        }
        data.value = posts
        stepForSharedCounter *= 2
    }

    override fun viewById(id : Long) {
        posts = posts.map {
            if(it.id != id) {
                it }
            else {
                it.copy(viewedCount = it.viewedCount + 1)
            }
        }
        data.value = posts
    }
}