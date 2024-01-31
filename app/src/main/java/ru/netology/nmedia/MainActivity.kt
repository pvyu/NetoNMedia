package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.log10

class MainActivity : AppCompatActivity() {

    fun FormatCountValue(count : Int) : String = when((log10(count.toDouble()) + 1).toInt()) {
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

        val post = Post(id = 1,
                        author = "author",
                        published = "published",
                        content = "content",
                        likesCount = 0,
                        likedByMe = false,
                        sharedCount = 0,
                        viewedCount  = 0
        )

        var StepForShaeredCountyer : Int = 1

        with(binding) {
            txtAuthor.text = post.author
            txtPublished.text = post.published
            txtContent.text = post.content
            txtLiked.text = FormatCountValue(post.likesCount)
            txtShared.text = FormatCountValue(post.sharedCount)
            txtViewed.text = "0"
        }

        binding.root.setOnClickListener {
            println("binding.root.setOnClickListener")
        }

        binding.avatar.setOnClickListener {
            println("binding.avatar.setOnClickListener")
        }

        binding.btnLiked.setOnClickListener {
            println("binding.btnLiked.setOnClickListener")
            post.likedByMe =!post.likedByMe
            if (post.likedByMe) {
                post.likesCount++;
                binding.btnLiked.setImageResource(R.drawable.baseline_favorite_red_24)
            }
            else {
                post.likesCount--;
                binding.btnLiked.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            binding.txtLiked.text = FormatCountValue(post.likesCount)
       }

        binding.btnShared.setOnClickListener {
            println("binding.btnShared.setOnClickListener")
            post.sharedCount += StepForShaeredCountyer
            println(post.sharedCount)
            binding.txtShared.text = FormatCountValue(post.sharedCount)

            StepForShaeredCountyer *= 2
        }










    }
}