package com.example.calmify.NOTE_PAGE

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calmify.NOTE_PAGE.MODELS.Note
import com.example.calmify.R
import com.example.calmify.databinding.ActivityNotePageBinding
import com.example.calmify.databinding.ActivitySignInBinding

class NotePageActivity : AppCompatActivity(), NoteClickEditInterface, NoteClickDeleteInterface {

    private lateinit var binding: ActivityNotePageBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        binding.idMyNotesRecyclerView.layoutManager= LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this,this,this)
        binding.idMyNotesRecyclerView.adapter= noteAdapter

        viewModel=ViewModelProvider(this, ViewModelProvider
            .AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        viewModel.allNote.observe(this, Observer { list->
            list?.let{
                noteAdapter.updateList(it)
            }
        })

        binding.idAddNoteFBbtn.setOnClickListener{
            val intent= Intent(this,WriteNoteActivity::class.java)
            startActivity(intent)
//            this.finish()
        }

    }

    override fun onNoteToEditItemClicked(note: Note) {

        //for passing data into Edit page
        val intent= Intent(this,WriteNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteID",note.id)

        startActivity(intent)
//        finish()
    }

    override fun onNoteToDeleteItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}