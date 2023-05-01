package com.example.calmify.NOTE_PAGE

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.calmify.NOTE_PAGE.MODELS.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//communicator btwn model and views: store data and pass data to views
class NoteViewModel (application: Application): AndroidViewModel(application){

    val allNote: LiveData<List<Note>>
    val repository:NoteRepository

    init {
        val dao =NoteDatabase.getDatabase(application).getNotesDao()
        repository= NoteRepository(dao)
        allNote= repository.allNotes
    }

    //coroutines for background thread:
    fun deleteNote(note: Note)= viewModelScope.launch(Dispatchers.IO){
        repository.delete_myNote(note)
    }

    fun updateNote(note: Note)= viewModelScope.launch(Dispatchers.IO){
        repository.update_myNote(note)
    }
    fun addNote(note: Note)= viewModelScope.launch(Dispatchers.IO){
        repository.insert_myNote(note)
    }

}