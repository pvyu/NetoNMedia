package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.log10


//--------------------------------------------------------------------------------------------------

typealias OnPostChanged = (post: Post) -> Unit
//--------------------------------------------------------------------------------------------------

interface IOnInteractionListener {
    fun onLike(post : Post)
    fun onShare(post : Post)
    fun onView(post : Post)
    fun onRemove(post : Post)
    fun onEdit(post : Post)
}
//--------------------------------------------------------------------------------------------------



class PostsAdapter(private val onInteractionListener :  IOnInteractionListener,
                   ) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
//--------------------------------------------------------------------------------------------------

class PostViewHolder(private val binding: CardPostBinding,
                     private val listeners : IOnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    private fun formatCountValue(count : Int) : String = when((log10(count.toDouble() + 1)).toInt()) {
        in 0..3 -> { count.toString() }
        in 4..6  -> { String.format("%.1f", count / 1000.0) + "K" }
        else -> { String.format("%.1f", count / 1000000.0) + "M"  }
    }

    fun bind(post: Post) {
        with(binding) {
            txtAuthor.text = post.author
            txtPublished.text = post.published
            txtContent.text = post.content
            btnLiked.isChecked = post.likedByMe
            btnLiked.text = formatCountValue(post.likesCount)

//            btnLiked.setImageResource(
//                if (post.likedByMe) {
//                    R.drawable.baseline_favorite_red_24
//                }
//                else {
//                    R.drawable.baseline_favorite_border_24
//                }
//            )
//            txtLiked.text = formatCountValue(post.likesCount)

            btnShared.text = formatCountValue(post.sharedCount)
            btnViewed.text = formatCountValue(post.viewedCount)

            //----------------------------------------------------

            btnLiked.setOnClickListener {
                listeners.onLike(post)
            }

            btnShared.setOnClickListener {
                listeners.onShare(post)
            }

            btnViewed.setOnClickListener {
                listeners.onView(post)
            }

            btnPostMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener {mnuItem ->
                        when (mnuItem.itemId) {
                            R.id.mnuRemovePost -> {
                                listeners.onRemove(post)
                                true
                            }
                            R.id.mnuEditPost -> {
                                listeners.onEdit(post)
                                true
                            }
                            else -> false
                        } // when (it.itemId)
                    } // setOnMenuItemClickListener {}
                }.show()
            }



        } // with(binding)
    } // fun bind()
}
//--------------------------------------------------------------------------------------------------

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem


    }


}
//--------------------------------------------------------------------------------------------------

