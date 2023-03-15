package com.example.kyoassistance.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kyoassistance.R
import com.example.kyoassistance.pojo.GmailMessage
import com.example.kyoassistance.ui.emailResponse.MailGenerationActivity
import de.hdodenhof.circleimageview.CircleImageView

class GmailMessagesAdapter(val activity:Activity):RecyclerView.Adapter<GmailMessagesAdapter.GmailMessageViewHolder>() {
    var arrayListMessages = arrayListOf<GmailMessage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GmailMessageViewHolder {
        return GmailMessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_message_item, parent, false))
    }

    override fun onBindViewHolder(holder: GmailMessageViewHolder, position: Int) {
        holder.senderName.text = arrayListMessages[position].senderName
        holder.senderMessage.text = arrayListMessages[position].message
        holder.messageDate.text = arrayListMessages[position].date
        holder.senderPhotoProfile.setImageResource(arrayListMessages[position].senderPhotoProfile)
        holder.itemView.setOnClickListener {
            val intent = Intent(activity ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,arrayListMessages[position].message)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayListMessages.size
    }

    inner class GmailMessageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val senderName = itemView.findViewById<TextView>(R.id.sender_name)
        val senderMessage = itemView.findViewById<TextView>(R.id.sender_last_message)
        val messageDate = itemView.findViewById<TextView>(R.id.message_date)
        val senderPhotoProfile = itemView.findViewById<CircleImageView>(R.id.senderPhotoProfile)
    }

    fun setList(arrayListGmailMessages:java.util.ArrayList<GmailMessage>)
    {
        this.arrayListMessages = arrayListGmailMessages
        notifyDataSetChanged()
    }
}