package com.example.kyoassistance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.kyoassistance.R
import com.example.kyoassistance.database.Entity.NoteEntity

class NotesAdapter(val context : Context, private val dataSet : List<NoteEntity>):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.textViewNoteContent.setText(dataSet[position].content)
        holder.imageViewButtonDelete.setOnClickListener {
            delChatLayoutClick?.onClick(it ,position)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface DelChatLayoutClick {
        fun onClick(view : View, position: Int)
    }
    var delChatLayoutClick : DelChatLayoutClick? = null

    inner class NoteViewHolder(itemView:View):ViewHolder(itemView){
        val textViewNoteContent = itemView.findViewById<TextView>(R.id.textViewNoteContent)
        val imageViewButtonDelete = itemView.findViewById<ImageView>(R.id.imageViewButtonDelete)

    }
}