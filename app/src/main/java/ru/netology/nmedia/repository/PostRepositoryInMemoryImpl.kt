package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostVideo

class PostRepositoryInMemoryImpl : PostRepository {
    private var stepForSharedCounter : Int = 1

    //todo:
    // post : MutableList<Post> = mutableListOf<Post>
    // Допустимо ли использовать MutableList, будет лди от корректно преобзоваван к
    // LiveData<List<Post>> для использования сторонними классами

    private var nextPostId : Long = 0L

    private var posts : List<Post> = listOf<Post> (
        Post(
            id = ++nextPostId,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "04 февракля в 21:43",
        ),
        Post(
            id = ++nextPostId,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это второй!",
            published = "08 февракля в 21:43",
            vedeo = PostVideo("Станислав Дробышевский. Выбор профессии и работа с точки зрения науки", "https://youtu.be/DA2S9UEGF7c", 0)
        ),
        Post(
            id = ++nextPostId,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это третий!",
            published = "08 февракля в 21:43",
        ),
        Post(
            id = ++nextPostId,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это четвёртый!",
            published = "08 февракля в 21:43",
        ),
        Post(
            id = ++nextPostId,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это пятый!",
            published = "08 февракля в 21:43",
        ),
        Post(
            id = ++nextPostId,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это шестой!",
            published = "08 февракля в 21:43",
        ),
        Post(
            id = ++nextPostId,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это седьмой!",
            published = "08 февракля в 21:43",
        ),
    )

    private val data : MutableLiveData<List<Post>> = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    //----------------------------------------------------------------------------------------------

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(post.copy(id = ++nextPostId,
                                     published = if (post.published.isEmpty()) { "No date" } else { post.published },
                                     author = if (post.author.isEmpty()) { "No author" } else { post.author } )
                    ) + posts
        }
        else {
            posts = posts.map { if (it.id != post.id) it else it.copy(content = post.content) }
        }
        data.value = posts
    }
    //----------------------------------------------------------------------------------------------

    override fun likeById(id : Long) {
        posts = posts.map {
            if (it.id != id) {
                it }
            else {
                it.copy(likedByMe = !it.likedByMe,
                        likesCount = if (!it.likedByMe) { it.likesCount + 1 } else { it.likesCount - 1 } )
            }
        }
        data.value = posts
    }
    //----------------------------------------------------------------------------------------------

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
    //----------------------------------------------------------------------------------------------

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
    //----------------------------------------------------------------------------------------------

    override fun removeById(id : Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
    //----------------------------------------------------------------------------------------------

}
//--------------------------------------------------------------------------------------------------
