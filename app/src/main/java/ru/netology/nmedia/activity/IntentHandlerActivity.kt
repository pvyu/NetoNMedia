package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            val text : String? = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, getString(R.string.strErrorEmptyContent), Snackbar.LENGTH_INDEFINITE).
                        setAction(android.R.string.ok) {
                            finish()
                        }.show()
            } // if text.isNullOrBlank()

            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        } // intent?.let {}


    } // onCreate{}
}