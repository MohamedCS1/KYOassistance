package com.example.kyoassistance.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kyoassistance.database.Entity.NoteEntity

@Dao
interface NotesDAO {

    @Query("SELECT * FROM NotesTable")
    fun getNotesData() : List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(content : NoteEntity)

    @Query("DELETE FROM NotesTable WHERE id = :id")
    fun deleteSelectedNote(id : Int)

}