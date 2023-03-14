package com.example.kyoassistance.ui.emailResponse

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.example.kyoassistance.R
import com.example.kyoassistance.database.Entity.ContentEntity
import com.example.kyoassistance.database.Entity.NoteEntity
import com.example.kyoassistance.databinding.ActivityMailGenerationBinding
import com.example.kyoassistance.viewModel.MainViewModel
import java.util.ArrayList

class MailGenerationActivity : AppCompatActivity() {

    lateinit var binding: ActivityMailGenerationBinding
    private val viewModel : MainViewModel by lazy {
        MainViewModel(this)
    }
    var isFirstObservation = true
    var isSecondObservation = true
    private var contentDataList = ArrayList<ContentEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMailGenerationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.editTextMailGenerated.setText("")

        viewModel.getContentData()

        viewModel.contentList.observe(this, Observer {
            if (isFirstObservation)
            {
                Toast.makeText(this ,"is first",Toast.LENGTH_SHORT).show()
                isFirstObservation = false
                return@Observer
            }
            if (isSecondObservation)
            {
                isSecondObservation = false
                return@Observer
            }

            contentDataList.clear()
            for (entity in it) {
                contentDataList.add(entity)
            }
            binding.editTextMailGenerated.setText(contentDataList.last().content.trim())

            Toast.makeText(this ,contentDataList.last().content,Toast.LENGTH_SHORT).show()

        })


        val emailContent = intent.extras?.getString("mailContent" ,"")

        binding.editTextMessageContent.setText(emailContent)

        binding.buttonGenerate.setOnClickListener {
            viewModel.postResponse("response to this mail $emailContent")
            viewModel.insertContent("response to this mail $emailContent", 2)
            viewModel.getContentData()
        }

        binding.imageViewBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.imageViewButtonSave.setOnClickListener {
            viewModel.insertNote(NoteEntity(0 ,binding.editTextMailGenerated.text.toString()))
            Toast.makeText(this ,"Saved" ,Toast.LENGTH_SHORT).show()
        }

        binding.imageViewButtonSend.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"


            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, binding.editTextMailGenerated.text.toString())

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."))
                finish()
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "There is no email client installed.", Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.imageViewButtonCopy.setOnClickListener {
            val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", binding.editTextMailGenerated.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this ,"Copied" ,Toast.LENGTH_SHORT).show()
        }
    }
}