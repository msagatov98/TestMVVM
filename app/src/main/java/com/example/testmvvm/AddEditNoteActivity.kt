package com.example.testmvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddEditNoteActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ID = "ID"
        val EXTRA_TITLE = "title"
        val EXTRA_PRIORITY = "priority"
        val EXTRA_DESCRIPTION = "description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        window.statusBarColor = resources.getColor(R.color.purple_700)

        numberPriority.minValue = 0
        numberPriority.maxValue = 10

        if (intent.hasExtra(EXTRA_ID)) {
            tvHeader.text = "Edit note"

            inputTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            inputDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))

            numberPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 0)

        } else {
            tvHeader.text = "Add note"
        }

    }

    fun onClick(view: View) {
        when(view) {
            icBack -> finish()
            icSave -> saveNote()
        }
    }

    private fun saveNote() {
        val title = inputTitle.text.toString().trim()
        val description = inputDescription.text.toString().trim()
        val priority = numberPriority.value

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Enter title and description", Toast.LENGTH_SHORT).show()
        } else {

            val data = Intent()

            data.putExtra(EXTRA_TITLE, title)
            data.putExtra(EXTRA_DESCRIPTION, description)
            data.putExtra(EXTRA_PRIORITY, priority)

            val id = intent.getIntExtra(EXTRA_ID, -1)

            if (id != -1)
                data.putExtra(EXTRA_ID, id)

            setResult(RESULT_OK, data)
            finish()
        }
    }
}