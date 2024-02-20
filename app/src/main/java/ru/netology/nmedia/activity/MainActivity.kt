package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.IOnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard
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


        val newPostLauncher = registerForActivityResult(NewPostContract) {result ->
            result ?: return@registerForActivityResult
            viewModel.changeContentAndSave(result)
        }


        val adapter = PostsAdapter(object : IOnInteractionListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }
                override fun onShare(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val chooser = Intent.createChooser(intent, getString(R.string.strSharePostTitle))
                    //startActivity(intent)
                    startActivity(chooser)

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
            }
        )

        //todo: нельзя ли здесь получить более подробную информацию об изменении данных, id поста, например?
        // Либо, подписаться на некую встпомагательную структуру данных? Чтобы попытаться минимизировать копирование.
        // В этом случае ListAdaptor будет, скорее всего, не нужен?
        viewModel.data.observe(this) { posts ->
            val isNewPost : Boolean = (adapter.currentList.size < posts.size) && adapter.currentList.size > 0
            adapter.submitList(posts) {
                if (isNewPost) {
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
        } // viewModel.data.observe(){}

        binding.recyclerView.adapter = adapter

        viewModel.editedPost.observe(this) {post ->
            if (post.id != 0L) {
                binding.txtEditPostContentShort.setText(post.content)
                binding.groupEditing.visibility = View.VISIBLE
                binding.editPostContent.focusAndShowKeyboard()  // .requestFocus() не показывает клавиатуру
                binding.editPostContent.setText(post.content)
            }
        }


        binding.btnSavePost.setOnClickListener {
            newPostLauncher.launch(null)



//            val text : String = binding.editPostContent.text.toString().trim()
//            if (text.isEmpty()) {
//                Toast.makeText(this, R.string.strErrorEmptyContent, Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }
//            viewModel.changeContentAndSave(text)
//
//            binding.groupEditing.visibility = View.GONE
//            binding.editPostContent.setText("")
//            binding.editPostContent.clearFocus()
//            AndroidUtils.hideKeyboard(it)
//
//            // binding.recyclerView.smoothScrollToPosition(0) // moved to adapter.submitList(posts)
        }

        binding.btnCancelEditing.setOnClickListener {
            viewModel.cancelEditing()

            binding.groupEditing.visibility = View.GONE
            binding.editPostContent.setText("")
            binding.editPostContent.clearFocus()
            AndroidUtils.hideKeyboard(it)
        }


    } //override fun onCreate()
}