package com.example.testmvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val noteRepasitory: NoteRepository
    private val allNotes: LiveData<List<Note>>

    init {
        noteRepasitory = NoteRepository(application)
        allNotes = noteRepasitory.getAllNotes()
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepasitory.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepasitory.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepasitory.delete(note)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepasitory.deleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return noteRepasitory.getAllNotes()
    }
}