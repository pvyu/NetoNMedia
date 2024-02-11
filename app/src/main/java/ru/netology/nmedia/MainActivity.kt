package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo:
        // разобраться как инстанцировать viewModel через объявление viewModel:
        // class PostViewModel(private var repository: PostRepository) : ViewModel() {
        //   fun chanchReposytory(postRepository : PostRepository) {}
        // }
        // через переопределение параметра “фабрика” у функции viewModels -
        // через этот параметр можно указывать вызов конструктора, если отличается от того, что по умолчанию.

        val viewModel : PostViewModel by viewModels()

        val adapter = PostsAdapter({post: Post -> viewModel.likeById(post.id)},
                                   {post: Post -> viewModel.shareById(post.id)},
                                   {post: Post -> viewModel.viewById(post.id)})

        //todo: нельзя ли здесь получить более подробную информацию об изменении данных, id поста, например?
        // Либо, подписаться на некую встпомагательную структуру данных? Чтобы попытаться минимизировать копирование.
        // В этом случае ListAdaptor будет, скорее всего, не нужен?
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        } // viewModel.data.observe(){}

        binding.root.adapter = adapter
    } //override fun onCreate()
}