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
import com.example.kyoassistance.Utils.LoadingDialog
import com.example.kyoassistance.database.Entity.ContentEntity
import com.example.kyoassistance.database.Entity.NoteEntity
import com.example.kyoassistance.databinding.ActivityMailGenerationBinding
import com.example.kyoassistance.network.Apis
import com.example.kyoassistance.network.RetrofitInstance
import com.example.kyoassistance.pojo.GptResponse
import com.example.kyoassistance.pojo.GptText
import com.example.kyoassistance.repository.DatabaseRepository
import com.example.kyoassistance.viewModel.MainViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MailGenerationActivity : AppCompatActivity() {


    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    lateinit var binding: ActivityMailGenerationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMailGenerationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.editTextMailGenerated.setText("")

        val emailContent = intent.extras?.getString("mailContent" ,"")

        binding.editTextMessageContent.setText(emailContent)




        binding.buttonGenerate.setOnClickListener {
            loadingDialog.show()
            val jsonObject: JsonObject = JsonObject().apply{
                addProperty("model", "text-davinci-003")
                addProperty("prompt", "response for this email$emailContent")
                addProperty("temperature", 0)
                addProperty("max_tokens", 4000)
            }
            RetrofitInstance.getInstance().create(Apis::class.java).postRequest(jsonObject).enqueue(object :Callback<GptResponse>{
                override fun onResponse(call: Call<GptResponse>, response: Response<GptResponse>) {
                    val gson = Gson()
                    val tempjson = gson.toJson(response.body()?.choices?.get(0))
                    val tempgson = gson.fromJson(tempjson, GptText::class.java)
                    binding.editTextMailGenerated.setText(tempgson.text)
                    loadingDialog.hide()
                }

                override fun onFailure(call: Call<GptResponse>, t: Throwable) {
                    Toast.makeText(this@MailGenerationActivity ,"Something went wrong please try again" ,Toast.LENGTH_SHORT).show()
                    loadingDialog.hide()
                }
            })
        }

        binding.imageViewBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.imageViewButtonSave.setOnClickListener {
            DatabaseRepository().insertNote(NoteEntity(0 ,binding.editTextMailGenerated.text.toString()))
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