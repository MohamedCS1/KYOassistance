package com.example.kyoassistance.ui.notes

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kyoassistance.R
import com.example.kyoassistance.adapters.NotesAdapter
import com.example.kyoassistance.database.Entity.ContentEntity
import com.example.kyoassistance.database.Entity.NoteEntity
import com.example.kyoassistance.databinding.ActivityNotesBinding
import com.example.kyoassistance.viewModel.MainViewModel
import java.util.ArrayList

class NotesActivity : AppCompatActivity() {

    lateinit var notesAdapter: NotesAdapter
    lateinit var binding: ActivityNotesBinding
    private val viewModel : MainViewModel by lazy {
        MainViewModel(this)
    }
    private var contentNotesList = ArrayList<NoteEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesAdapter = NotesAdapter(this ,contentNotesList)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        viewModel.getNotesData()

        viewModel.noteList.observe(this , Observer {notes ->
            contentNotesList.clear()
            for (note in notes)
            {
                contentNotesList.add(note)
            }
            sendDataToRecyclerView()
        })

        notesAdapter.delChatLayoutClick = object :NotesAdapter.DelChatLayoutClick{
            override fun onClick(view: View, position: Int) {
                val builder = AlertDialog.Builder(this@NotesActivity)
                builder.setTitle("Delete")
                    .setMessage("Do you really want to delete this message ?")
                    .setPositiveButton("Yes",
                        DialogInterface.OnClickListener { dialog, id ->
                            viewModel.deleteSelectedNote(contentNotesList[position].id)
                            viewModel.getNotesData()
                            sendDataToRecyclerView()

                        })
                    .setNegativeButton("No",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                builder.show()

            }
        }

        binding.imageViewBackButton.setOnClickListener {
            onBackPressed()
        }

    }

    fun sendDataToRecyclerView()
    {
        binding.RecyclerViewNotes.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}