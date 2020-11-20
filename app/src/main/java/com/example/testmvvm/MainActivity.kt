package com.example.testmvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    val ADD_NOTE_REQUEST = 0
    val EDIT_NOTE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NoteAdapter()

        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)

                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)

                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        noteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, {
            adapter.setNotes(notes = it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(recyclerView)
    }

    fun onClick(view: View) {
        when(view) {
            btnAddNote -> startActivityForResult(Intent(this, AddEditNoteActivity::class.java), ADD_NOTE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)!!
            val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)!!
            val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0)

            val note = Note(title, description, priority)

            noteViewModel.insert(note)

            Toast.makeText(this, "Noted added", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            val id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)

            if (id == -1) return

            val title = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)!!
            val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)!!
            val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0)

            val note = Note(title, description, priority)
            note.id = id

            noteViewModel.update(note)

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.delete_all_notes -> {
                    noteViewModel.deleteAll()
                    Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                }
        }

        return super.onOptionsItemSelected(item)
    }
}