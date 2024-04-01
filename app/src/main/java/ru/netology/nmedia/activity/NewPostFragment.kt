package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

interface IOnInteractionListener {
    fun onLike(post : Post)
    fun onShare(post : Post)
    fun onView(post : Post)
    fun onRemove(post : Post)
    fun onEdit(post : Post)
    fun onPostOpen(post: Post)
}
//--------------------------------------------------------------------------------------------------

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        //setContentView(binding.root)

        //-----------------------------------------------------------
        arguments?.let {
            if (it.textArg == null) {
                // Добавленеи нового поста
                binding.editPostContent.hint = getString(R.string.strEnterNewPostText)
                binding.txtEditMsg.text = getString(R.string.strNewPostAdding)
                binding.txtEditPostContentShort.text = ""
                binding.groupEditing.visibility = View.GONE
            } // if text.isNullOrBlank()
            else {
                // Редактирование поста
                binding.editPostContent.hint = getString(R.string.strEditPostText)
                binding.txtEditMsg.text = getString(R.string.strPostEditing)
                binding.txtEditPostContentShort.text = it.textArg
                binding.groupEditing.visibility = View.VISIBLE
                binding.editPostContent.setText(it.textArg)
            }
        }
        //-----------------------------------------------------------

        binding.editPostContent.focusAndShowKeyboard()  // .requestFocus() не показывает клавиатуру

        binding.btnOk.setOnClickListener {
            val text = binding.editPostContent.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContentAndSave(text)
            }
            else {
                viewModel.cancelEditing()
            }
            findNavController().navigateUp()
        }
        //-----------------------------------------------------------

        binding.btnCancelEditing.setOnClickListener {
            //activity?.setResult(Activity.RESULT_CANCELED)
            viewModel.cancelEditing()
            findNavController().navigateUp()
        }

        return binding.root
    } // onCreateView(){}
}
//--------------------------------------------------------------------------------------------------