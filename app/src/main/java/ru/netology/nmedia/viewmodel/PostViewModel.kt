package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

//todo:
// class PostViewModel(private var repository: PostRepository) : ViewModel() {}

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private var repository: PostRepository = PostRepositoryInMemoryImpl()

    fun chanchReposytory(postRepository : PostRepository) {
        repository = postRepository
    }

    val data = repository.get()

    fun like() = repository.like()
    fun share() = repository.share()
    fun view() = repository.view()
}