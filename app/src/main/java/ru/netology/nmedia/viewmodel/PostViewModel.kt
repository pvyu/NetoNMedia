package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl


private val emptyPost = Post()


//todo:
// class PostViewModel(private var repository: PostRepository) : ViewModel() {} ?

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private var repository: PostRepository = PostRepositoryInMemoryImpl()

    fun chanchReposytory(postRepository : PostRepository) {
        repository = postRepository
    }

    val data : LiveData<List<Post>> = repository.getAll()

    val editedPost : MutableLiveData<Post> = MutableLiveData<Post>(emptyPost)


    fun changeContentAndSave(newContent : String) {
        editedPost.value?.let {
            if (newContent != it.content) {
                repository.save(it.copy(content = newContent))
            }
            editedPost.value = emptyPost // null ?
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun viewById(id: Long) = repository.viewById(id)
    fun removeById(id : Long) = repository.removeById(id)

}