package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //-----------------------------------------------------------
        intent?.let {
            val text : String? = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text == null) {
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
                binding.txtEditPostContentShort.text = text
                binding.groupEditing.visibility = View.VISIBLE
                binding.editPostContent.setText(text)
                binding.editPostContent.focusAndShowKeyboard()  // .requestFocus() не показывает клавиатуру
            }
        } // intent?.let {}
        //-----------------------------------------------------------

        binding.btnOk.setOnClickListener {
            val text = binding.editPostContent.text.toString()
            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            }
            else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }
        //-----------------------------------------------------------

        binding.btnCancelEditing.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    } // onCreate(){}
}
//--------------------------------------------------------------------------------------------------

object NewPostContract : ActivityResultContract<String?, String?>() {
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, NewPostActivity::class.java).apply { putExtra(Intent.EXTRA_TEXT, input) }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(Intent.EXTRA_TEXT)
    }
}
//--------------------------------------------------------------------------------------------------
