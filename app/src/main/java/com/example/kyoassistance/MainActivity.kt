package com.example.kyoassistance

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kyoassistance.adapters.ContentAdapter
import com.example.kyoassistance.database.Entity.ContentEntity
import com.example.kyoassistance.databinding.ActivityMainBinding
import com.example.kyoassistance.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var branch : Int = 1
    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by lazy {
        MainViewModel()
    }
    private var contentDataList = ArrayList<ContentEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getContentData()

        viewModel.contentList.observe(this, Observer {
            contentDataList.clear()
            for (entity in it) {
                contentDataList.add(entity)
            }
            setContentListRV(branch)
        })

        viewModel.deleteCheck.observe(this, Observer {
            if (it == true) {
                viewModel.getContentData()
                branch = 1
            }
        })

        viewModel.gptInsertCheck.observe(this, Observer {
            if (it == true) {
                viewModel.getContentData()
                binding.loading.visibility = View.INVISIBLE
            }
            branch = 2
        })

        binding.buttonSendmessage.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            viewModel.postResponse(binding.edittextSendMessage.text.toString())
            viewModel.insertContent(binding.edittextSendMessage.text.toString(), 2)
            binding.edittextSendMessage.setText("")
            branch = 2
            viewModel.getContentData()
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
                Timber.tag("currentItem").e("${contentDataList[position].id}")

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Delete")
                    .setMessage("Delete this message ?")
                    .setPositiveButton("Yes",
                        DialogInterface.OnClickListener { dialog, id ->
                            viewModel.deleteSelectedContent(contentDataList[position].id)
                        })
                    .setNegativeButton("No",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                builder.show()
            }
        }
    }
}