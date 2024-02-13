package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

//todo:
// class PostViewModel(private var repository: PostRepository) : ViewModel() {} ?

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private var repository: PostRepository = PostRepositoryInMemoryImpl()

    fun chanchReposytory(postRepository : PostRepository) {
        repository = postRepository
    }

    val data = repository.getAll()

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun viewById(id: Long) = repository.viewById(id)

}