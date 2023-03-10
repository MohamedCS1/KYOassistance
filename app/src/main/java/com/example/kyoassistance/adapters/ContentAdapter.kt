package com.example.kyoassistance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kyoassistance.R
import com.example.kyoassistance.database.Entity.ContentEntity

class ContentAdapter(val context : Context, private val dataSet : List<ContentEntity>) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    companion object {
        private const val Gpt = 1
        private const val User = 2
    }

    interface DelChatLayoutClick {
        fun onLongClick(view : View, position: Int)
    }
    var delChatLayoutClick : DelChatLayoutClick? = null

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val contentTextView : TextView = view.findViewById(R.id.chatLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == Gpt) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.gpt_content_item, parent, false)
            return ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_content_item, parent, false)
            return ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contentTextView.text = dataSet[position].content.trim()

        holder.contentTextView.setOnLongClickListener { view ->
            delChatLayoutClick?.onLongClick(view, position)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].gptOrUser == 1) {
            Gpt
        } else {
            User
        }
    }

}