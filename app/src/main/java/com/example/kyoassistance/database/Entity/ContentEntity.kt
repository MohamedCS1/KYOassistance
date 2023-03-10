package com.example.kyoassistance.database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ContentTable")
data class ContentEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,
    @ColumnInfo(name = "Content")
    var content : String,
    @ColumnInfo(name = "gptOrUser")
    var gptOrUser : Int

)
