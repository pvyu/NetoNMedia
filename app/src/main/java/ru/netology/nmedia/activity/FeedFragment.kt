package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        //setContentView(binding.root)

        //todo:
        // разобраться как инстанцировать viewModel через объявление viewModel:
        // class PostViewModel(private var repository: PostRepository) : ViewModel() {
        //   fun chanchReposytory(postRepository : PostRepository) {}
        // }
        // через переопределение параметра “фабрика” у функции viewModels -
        // через этот параметр можно указывать вызов конструктора, если отличается от того, что по умолчанию.

        //todo: ownerProducer = ::requireParentFragment - зачем?
        val viewModel : PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        //------------------------------------------------------------------------------------

        val adapter = PostsAdapter(object : PostInteractionListener(viewModel, this.requireContext()) {
                                        override fun onPostOpen(post: Post) {
                                            findNavController().navigate(
                                                R.id.action_feedFragment_to_onePostFragment,
                                                Bundle().apply { textArg = post.id.toString() } )
                                        }
                                   }
        )
        //------------------------------------------------------------------------------------

        //todo: нельзя ли здесь получить более подробную информацию об изменении данных, id поста, например?
        // Либо, подписаться на некую встпомагательную структуру данных? Чтобы попытаться минимизировать копирование.
        // В этом случае ListAdaptor будет, скорее всего, не нужен?
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val isNewPost : Boolean = (adapter.currentList.size < posts.size) && adapter.currentList.size > 0
            adapter.submitList(posts) {
                if (isNewPost) {
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
        } // viewModel.data.observe(){}

        binding.recyclerView.adapter = adapter
        //------------------------------------------------------------------------------------

        viewModel.editedPost.observe(viewLifecycleOwner) {post ->
            if (post.id != 0L) {
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                                             Bundle().apply { textArg = post.content }
                )
                //newPostLauncher.launch(post.content)
            }
        }
        //------------------------------------------------------------------------------------

        binding.btnAddPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
            //newPostLauncher.launch(null)
        }
        //------------------------------------------------------------------------------------


        return binding.root
    } //override fun onCreateView()
}