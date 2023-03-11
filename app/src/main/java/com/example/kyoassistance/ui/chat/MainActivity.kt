package com.example.kyoassistance.ui.chat

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kyoassistance.R
import com.example.kyoassistance.adapters.ContentAdapter
import com.example.kyoassistance.database.Entity.ContentEntity
import com.example.kyoassistance.database.Entity.NoteEntity
import com.example.kyoassistance.databinding.ActivityMainBinding
import com.example.kyoassistance.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_SPEECH_INPUT: Int = 10
    private var branch : Int = 1
    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by lazy {
        MainViewModel()
    }
    lateinit var textToSpeech:TextToSpeech
    private var contentDataList = ArrayList<ContentEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        viewModel.getContentData()

        viewModel.contentList.observe(this, Observer {
            contentDataList.clear()
            for (entity in it) {
                contentDataList.add(entity)
            }
            if (binding.buttonSwitchSF.isChecked)
            {
                if(contentDataList.last().gptOrUser == 1)
                {
                    textToSpeech.speak(contentDataList.last().content.toString(),TextToSpeech.QUEUE_FLUSH ,null)
                }
            }
            setContentListRV(branch)
        })

        viewModel.deleteMessageCheck.observe(this, Observer {
            if (it == true) {
                viewModel.getContentData()
                branch = 1
            }
        })

        viewModel.gptInsertMessageCheck.observe(this, Observer {
            if (it == true) {
                viewModel.getContentData()
                binding.loading.visibility = View.INVISIBLE
            }
            branch = 2
        })

        binding.buttonSendmessage.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            if (binding.edittextSendMessage.text.toString().isBlank())
            {
                return@setOnClickListener
            }
            viewModel.postResponse(binding.edittextSendMessage.text.toString())
            viewModel.insertContent(binding.edittextSendMessage.text.toString(), 2)
            binding.edittextSendMessage.setText("")
            branch = 2
            viewModel.getContentData()
        }

        binding.buttonSendVoice.setOnLongClickListener(object :OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {

                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
                )
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

                try {
                    startActivityForResult(intent ,REQUEST_CODE_SPEECH_INPUT)
                }catch (ex:java.lang.Exception)
                {
                    Toast.makeText(baseContext ,ex.message.toString() ,Toast.LENGTH_SHORT).show()
                }
                return true
            }
        })

        binding.imageViewBackButton.setOnClickListener {
            onBackPressed()
        }

        textToSpeech = TextToSpeech(
            applicationContext
        ) { i ->
            if (i != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        }


    }

    private fun setContentListRV(branch : Int) {
        val contentAdapter = ContentAdapter(this, contentDataList)
        binding.RecyclerViewContainer.adapter = contentAdapter
        binding.RecyclerViewContainer.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            binding.SVContainer.fullScroll(ScrollView.FOCUS_DOWN);
            if (branch != 1) {
                binding.edittextSendMessage.requestFocus()
            }
        }
        contentAdapter.delChatLayoutClick = object : ContentAdapter.DelChatLayoutClick {
            override fun onLongClick(view : View, position: Int) {

                val bottomSheetDialog = BottomSheetDialog(this@MainActivity ,
                    R.style.TransparentBackgroundDialog
                )
                bottomSheetDialog.setContentView(R.layout.bottom_dialog)
                bottomSheetDialog.show()

                val buttonSend = bottomSheetDialog.findViewById<LinearLayout>(R.id.buttonSend)
                val buttonSaveAsNote = bottomSheetDialog.findViewById<LinearLayout>(R.id.buttonSaveAsNote)
                val buttonDelete = bottomSheetDialog.findViewById<LinearLayout>(R.id.buttonDelete)
                val buttonCopy = bottomSheetDialog.findViewById<LinearLayout>(R.id.buttonCopy)

                buttonCopy?.setOnClickListener {
                    val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("label", contentDataList[position].content.trim())
                    clipboard.setPrimaryClip(clip)
                    bottomSheetDialog.hide()

                    Toast.makeText(this@MainActivity ,"Copied" ,Toast.LENGTH_SHORT).show()
                }

                buttonDelete?.setOnClickListener {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Delete")
                        .setMessage("Do you really want to delete this message ?")
                        .setPositiveButton("Yes",
                            DialogInterface.OnClickListener { dialog, id ->
                                viewModel.deleteSelectedContent(contentDataList[position].id)
                                bottomSheetDialog.hide()
                            })
                        .setNegativeButton("No",
                            DialogInterface.OnClickListener { dialog, id ->
                                bottomSheetDialog.hide()
                            })
                    builder.show()
                }

                buttonSend?.setOnClickListener {


                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.data = Uri.parse("mailto:")
                    emailIntent.type = "text/plain"


                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject")
                    emailIntent.putExtra(Intent.EXTRA_TEXT, contentDataList[position].content)

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."))
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            this@MainActivity,
                            "There is no email client installed.", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                Timber.tag("currentItem").e("${contentDataList[position].id}")

                buttonSaveAsNote?.setOnClickListener {
                    viewModel.insertNote(NoteEntity(0 ,contentDataList[position].content.trim()))
                    bottomSheetDialog.hide()
                    Toast.makeText(this@MainActivity ,"Saved" ,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                binding.edittextSendMessage.setText("")
                binding.edittextSendMessage.setText(
                    Objects.requireNonNull(result)?.get(0) ?:""
                )
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}