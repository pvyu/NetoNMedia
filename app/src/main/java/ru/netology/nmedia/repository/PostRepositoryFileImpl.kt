package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostVideo
import kotlin.reflect.typeOf

class PostRepositoryFileImpl(
    private val context : Context
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"

    private var stepForSharedCounter : Int = 1

    //todo:
    // post : MutableList<Post> = mutableListOf<Post>
    // Допустимо ли использовать MutableList, будет лди от корректно преобзоваван к
    // LiveData<List<Post>> для использования сторонними классами

    private var nextPostId : Long = 0L

    private var posts : List<Post> = emptyList()
        private set(value) {
            field = value
            data.value = value
            sync()
        }

    private val data : MutableLiveData<List<Post>> = MutableLiveData(posts)

    init {
        if (context.filesDir.resolve(filename).exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                nextPostId = posts.maxOfOrNull { post -> post.id }?.inc() ?: 0
            }
        }
        else {
            sync()
        }
    }
    //----------------------------------------------------------------------------------------------

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
    //----------------------------------------------------------------------------------------------

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
    }
    //----------------------------------------------------------------------------------------------

    override fun removeById(id : Long) {
        posts = posts.filter { it.id != id }
    }
    //----------------------------------------------------------------------------------------------

}
//--------------------------------------------------------------------------------------------------
