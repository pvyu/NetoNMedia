package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.log10

class MainActivity : AppCompatActivity() {

    fun FormatCountValue(count : Int) : String = when((log10(count.toDouble() + 1)).toInt()) {
        in 0..3 -> { count.toString() }
        in 4..6  -> { String.format("%.1f", count / 1000.0) + "K" }
        else -> { String.format("%.1f", count / 1000000.0) + "M"  }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
//        findViewById<ImageButton>(R.id.btnLiked).setOnClickListener {
//            if (it !is ImageButton) {
//                return@setOnClickListener
//            }
//            it.setImageResource(R.drawable.baseline_favorite_red_24)
//        }

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

        viewModel.data.observe(this) { posts ->
            posts.forEach { post->
                CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
                    txtAuthor.text = post.author
                    txtPublished.text = post.published
                    txtContent.text = post.content
                    btnLiked.setImageResource(
                        if (post.likedByMe) { R.drawable.baseline_favorite_red_24 } else { R.drawable.baseline_favorite_border_24 }
                    )
                    txtLiked.text = FormatCountValue(post.likesCount)
                    txtShared.text = FormatCountValue(post.sharedCount)
                    txtViewed.text = FormatCountValue(post.viewedCount)
                }
            }




        }

//        binding.btnLiked.setOnClickListener {
//            viewModel.like()
//        }
//
//        binding.btnShared.setOnClickListener {
//            viewModel.share()
//        }
//
//        binding.btnViewed.setOnClickListener {
//            viewModel.view()
//        }


    }
}