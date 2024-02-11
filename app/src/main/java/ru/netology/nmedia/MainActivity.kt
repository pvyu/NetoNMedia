package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.log10

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo:
        // 1. Как передать объект конкретного репозитария?
        // 2. viewModel и,как следствие, репозитарий создаются в активити, и,
        // следовательно, должны пересоздаваться вместе с ней.
        // Почему этого не происходит?
        // 3. Сейчас viewModel фактически является репозитаерием, не ясно как их отделить, и
        // где лучше разместитиьть/хранить репозиторий.
        // 4. Не разобрался как инстанцировать viewModel через объявление viewModel:
        // class PostViewModel(private var repository: PostRepository) : ViewModel() {
        //   fun chanchReposytory(postRepository : PostRepository) {}
        // }
        // и каким образом можно подменить репозиторий в рантайм.

        val viewModel : PostViewModel by viewModels()

        val adapter = PostsAdapter({post: Post -> viewModel.likeById(post.id)},
                                   {post: Post -> viewModel.shareById(post.id)},
                                   {post: Post -> viewModel.viewById(post.id)})

        //todo: нельзя ли сдесь получить более подробную информацию об изменении данных, id поста, например?
        // Либо, подписаться на некую встпомагательную структуру данных? Чтобы попытаться минимизировать копирование?
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        } // viewModel.data.observe(){}

        binding.root.adapter = adapter
    } //override fun onCreate()
}