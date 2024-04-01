package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentOnePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class OnePostFragment : Fragment() {

    val viewModel : PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentOnePostBinding.inflate(inflater, container, false)

        val viewHolder = PostViewHolder(view.postLayout,
                                        object : PostInteractionListener(viewModel, this.requireContext()) {
                                            override fun onPostOpen(post: Post) {}
                                            override fun onEdit(post: Post) {
                                                findNavController().navigateUp()
                                                super.onEdit(post)
                                            }
                                        }
        )

        val id = arguments?.textArg?.toLong() ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == id } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }
        return view.root
    }

}