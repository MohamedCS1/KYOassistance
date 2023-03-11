package com.example.kyoassistance.ui.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        MainViewModel()
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
                viewModel.deleteSelectedNote(contentNotesList[position].id)
            }
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