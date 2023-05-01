package com.example.calmify.NOTE_PAGE

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calmify.NOTE_PAGE.MODELS.Note
import com.example.calmify.R
import org.w3c.dom.Text
import java.lang.reflect.Array

class NoteAdapter(
    val context:Context,
    val noteClickedToEditInterface:NoteClickEditInterface,
    val noteClickedToDeleteInterface:NoteClickDeleteInterface
): RecyclerView.Adapter<NoteAdapter.myViewHolder>() {

    private val allNoteList =ArrayList<Note>()

    inner class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        val noteTitle_TV= itemView.findViewById<TextView>(R.id.id_noteTitle)
        val noteDescription_TV= itemView.findViewById<TextView>(R.id.id_noteDescription)
        val noteTimeStamp_TV= itemView.findViewById<TextView>(R.id.id_noteTimeStamp)
        val noteDelete_IV= itemView.findViewById<ImageButton>(R.id.id_deleteNoteBtn)
        val noteEdit_IV= itemView.findViewById<ImageButton>(R.id.id_EditNoteBtn)
    }

    //methods:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.noteTitle_TV.setText(allNoteList.get(position).noteTitle)
        holder.noteDescription_TV.setText(allNoteList.get(position).noteDescription)
        holder.noteTimeStamp_TV.setText("Last updated: "+allNoteList.get(position).timeStamp)

        holder.noteDelete_IV.setOnClickListener {
            noteClickedToDeleteInterface.onNoteToDeleteItemClicked(allNoteList.get(position))
        }
        holder.noteEdit_IV.setOnClickListener{
            noteClickedToEditInterface.onNoteToEditItemClicked(allNoteList.get(position))
        }
//        holder.itemView.setOnClickListener{
//            noteClickedToEditInterface.onNoteToEditItemClicked(allNoteList.get(position))
//        }
    }

    override fun getItemCount(): Int {
        return allNoteList.size
    }

    //for updating list on recycler:
    fun updateList(newList:List<Note>){
        allNoteList.clear()
        allNoteList.addAll(newList)
        notifyDataSetChanged()
    }
}


//for clicks on items and delete button:

interface NoteClickEditInterface{
    fun onNoteToEditItemClicked(note:Note)
}

interface NoteClickDeleteInterface{
    fun onNoteToDeleteItemClicked(note: Note)
}

