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
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


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
        arguments?.let{
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
                //activity?.setResult(Activity.RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            }
            else {
                viewModel.cancelEditing()
                //activity?.setResult(Activity.RESULT_CANCELED)
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

//object NewPostContract : ActivityResultContract<String?, String?>() {
//    override fun createIntent(context: Context, input: String?): Intent {
//        return Intent(context, NewPostFragment::class.java).apply { putExtra(Intent.EXTRA_TEXT, input) }
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?): String? {
//        return intent?.getStringExtra(Intent.EXTRA_TEXT)
//    }
//}
//--------------------------------------------------------------------------------------------------
