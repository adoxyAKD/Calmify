package com.example.calmify.NOTE_PAGE

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.calmify.NOTE_PAGE.MODELS.Note
import com.example.calmify.R
import com.example.calmify.databinding.ActivitySignUpBinding
import com.example.calmify.databinding.ActivityWriteNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class WriteNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteNoteBinding
    private lateinit var viewModel: NoteViewModel
    var noteID=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)


        viewModel=ViewModelProvider(this,ViewModelProvider
            .AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        val noteType= intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            //for editing
            val noteTitle=intent.getStringExtra("noteTitle")
            val noteDesc=intent.getStringExtra("noteDescription")

            noteID= intent.getIntExtra("noteID",-1)
            binding.idWriteTitleEdttxt.setText(noteTitle)
            binding.idWriteDescriptionEdttxt.setText(noteDesc)
            binding.idSaveORupdateBtn.setText("Update Note")

        }else{
            binding.idSaveORupdateBtn.setText("Save Notes")
        }

        binding.idSaveORupdateBtn.setOnClickListener{
            val noteTitle= binding.idWriteTitleEdttxt.text.toString()
            val noteDescription=binding.idWriteDescriptionEdttxt.text.toString()

            if(noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val simpleDateFomate = SimpleDateFormat("dd MM,yyyy - HH:mm")
                    val currentDate: String = simpleDateFomate.format(Date())

                    val updateMyNote = Note(noteTitle, noteDescription, currentDate)
                    updateMyNote.id = noteID
                    viewModel.updateNote(updateMyNote)

                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_SHORT).show()
                }
            } else {
//                for(i in 1..1000){
//                    addNotes(i)
//                }
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val simpleDateFomate = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate: String = simpleDateFomate.format(Date())


                    viewModel.addNote(Note(noteTitle, noteDescription, currentDate))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()

                }
            }
            startActivity(Intent(applicationContext, NotePageActivity::class.java))
            this.finish()
        }
    }

    fun addNotes(i:Int){
        val noteTitle= "Title no : "+i
        val noteDescription="Desc No :"+i

        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
            val simpleDateFomate = SimpleDateFormat("dd MM, yyyy - HH:mm")
            val currentDate: String = simpleDateFomate.format(Date())


            viewModel.addNote(Note(noteTitle, noteDescription, currentDate))
            Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}