package com.example.kyoassistance.adapters

import android.content.*
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.kyoassistance.R
import com.example.kyoassistance.database.Entity.NoteEntity

class NotesAdapter(val context : Context, private val dataSet : List<NoteEntity>):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.textViewNoteContent.text = dataSet[position].content
        holder.imageViewButtonDelete.setOnClickListener {
            delChatLayoutClick?.onClick(it ,position)
        }
        holder.imageViewButtonSend.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"


            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, dataSet[position].content)

            try {
                context.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    "There is no email client installed.", Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.imageViewButtonCopy.setOnClickListener {
            val clipboard: ClipboardManager = context.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", dataSet[position].content.trim())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context ,"Copied" ,Toast.LENGTH_SHORT).show()
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
        val imageViewButtonSend = itemView.findViewById<ImageView>(R.id.imageViewButtonSend)
        val imageViewButtonCopy = itemView.findViewById<ImageView>(R.id.imageViewButtonCopy)
    }
}