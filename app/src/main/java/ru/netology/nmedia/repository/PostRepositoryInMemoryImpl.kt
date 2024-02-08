package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var stepForSharedCounter : Int = 1

    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "04 февракля в 21:43",
        likesCount = 0,
        likedByMe = false,
        sharedCount = 0,
        viewedCount = 0,
    )
    private val data : MutableLiveData<Post> = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe,
                         likesCount = if (!post.likedByMe) { post.likesCount + 1 } else { post.likesCount - 1 })
        data.value = post
    }

    override fun share() {
        post = post.copy(sharedCount = post.sharedCount + stepForSharedCounter)
        data.value = post
        stepForSharedCounter *= 2
    }

    override fun view() {
        post = post.copy(viewedCount = post.viewedCount + 1)
        data.value = post
    }
}