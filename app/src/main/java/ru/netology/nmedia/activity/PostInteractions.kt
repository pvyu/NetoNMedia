package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

abstract class PostInteractionListener(private val viewModel : PostViewModel,
                              private val context: Context

) : IOnInteractionListener {
    override fun onLike(post: Post) {
        viewModel.likeById(post.id)
    }
    override fun onShare(post: Post) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, post.content)
        }
        val chooser = Intent.createChooser(intent, context.getString(R.string.strSharePostTitle))
        //startActivity(intent)
        context.startActivity(chooser)

        viewModel.shareById(post.id)
    }
    override fun onView(post: Post) {
        viewModel.viewById(post.id)
    }
    override fun onRemove(post: Post) {
        viewModel.removeById(post.id)
    }
    override fun onEdit(post: Post) {
        viewModel.edit(post)
    }
    override fun onPostOpen(post: Post) {
    }


}