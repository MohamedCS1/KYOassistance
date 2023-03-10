package com.example.kyoassistance.repository

import com.example.kyoassistance.App
import com.example.kyoassistance.database.ChatDatabase
import com.example.kyoassistance.database.Entity.ContentEntity

class DatabaseRepository {

    private val context = App.context()
    private val database = ChatDatabase.getDatabase(context)

    fun getContentData() = database.contentDAO().getContentData()

    fun insertContent(content : String, gptOrUser : Int) = database.contentDAO().insertContent(
        ContentEntity(0, content, gptOrUser)
    )

    fun deleteSelectedContent(id : Int) = database.contentDAO().deleteSelectedContent(id)

}