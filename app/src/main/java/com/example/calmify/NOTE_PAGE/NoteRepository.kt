package com.example.calmify.NOTE_PAGE

import androidx.lifecycle.LiveData
import com.example.calmify.NOTE_PAGE.MODELS.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes:LiveData<List<Note>> =noteDao.getAllNotes()

    //for insertion of note
//    suspend fun insert_myNote(note: Note){
     fun insert_myNote(note: Note){
        noteDao.insert_note(note)
    }

    //for deletion:
//    suspend fun delete_myNote(note: Note){
     fun delete_myNote(note: Note){
        noteDao.delete_note(note)
    }

    //for updation:
//    suspend fun update_myNote(note: Note){
     fun update_myNote(note: Note){
        noteDao.update_note(note)
    }
}
