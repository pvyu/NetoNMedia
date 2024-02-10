package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.log10

typealias OnPostChanged = (post: Post) -> Unit

class PostsAdapter(private val onLike : OnPostChanged,
                   private val onShare : OnPostChanged,
                   private val onView : OnPostChanged
                   ) : RecyclerView.Adapter<PostViewHolder>() {

    fun FormatCountValue(count : Int) : String = when((log10(count.toDouble() + 1)).toInt()) {
        in 0..3 -> { count.toString() }
        in 4..6  -> { String.format("%.1f", count / 1000.0) + "K" }
        else -> { String.format("%.1f", count / 1000000.0) + "M"  }
    }

    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLike, onShare, onView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(private val binding: CardPostBinding,
                     private val onLike: OnPostChanged,
                     private val onShare: OnPostChanged,
                     private val onView: OnPostChanged
) : RecyclerView.ViewHolder(binding.root) {

    fun FormatCountValue(count : Int) : String = when((log10(count.toDouble() + 1)).toInt()) {
        in 0..3 -> { count.toString() }
        in 4..6  -> { String.format("%.1f", count / 1000.0) + "K" }
        else -> { String.format("%.1f", count / 1000000.0) + "M"  }
    }

    fun bind(post: Post) {
        with(binding) {
            txtAuthor.text = post.author
            txtPublished.text = post.published
            txtContent.text = post.content
            btnLiked.setImageResource(
                if (post.likedByMe) {
                    R.drawable.baseline_favorite_red_24
                }
                else {
                    R.drawable.baseline_favorite_border_24
                }
            )
            txtLiked.text = FormatCountValue(post.likesCount)
            txtShared.text = FormatCountValue(post.sharedCount)
            txtViewed.text = FormatCountValue(post.viewedCount)

            btnLiked.setOnClickListener {
                onLike(post)
            }
            btnShared.setOnClickListener {
                onShare(post)
            }
            btnViewed.setOnClickListener {
                onView(post)
            }
        } // with(binding)
    } // fun bind()


}