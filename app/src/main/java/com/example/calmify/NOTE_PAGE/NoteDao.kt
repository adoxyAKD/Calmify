package com.example.calmify.NOTE_PAGE

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calmify.NOTE_PAGE.MODELS.Note

@Dao
interface NoteDao {

    //for Insertion of notes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert_note(note: Note)
//    suspend fun insert_note(note: Note)

    //for update:
    @Update
    fun update_note(note: Note)
//    suspend fun update_note(note: Note)

    //for deletion:
    @Delete
    fun delete_note(note: Note)
//    suspend fun delete_note(note: Note)

    //for read:
    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>


}