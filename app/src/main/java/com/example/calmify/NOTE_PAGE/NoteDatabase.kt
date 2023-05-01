package com.example.calmify.NOTE_PAGE

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calmify.NOTE_PAGE.MODELS.Note

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase(){

    abstract fun getNotesDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE:NoteDatabase? = null

        fun getDatabase(context:Context): NoteDatabase{

            //singleton pettern aproach:
            return INSTANCE?: synchronized(this){
                val instance =Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database" ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}