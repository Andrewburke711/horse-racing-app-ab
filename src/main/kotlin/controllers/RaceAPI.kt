package controllers

import ie.setu.models.Note
import utils.formatListString
import java.util.ArrayList

class NoteAPI() {

    private var notes = ArrayList<Note>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun add(note: Note): Boolean {
        note.noteId = getId()
        return notes.add(note)
    }

    fun delete(id: Int) = notes.removeIf { note -> note.noteId == id }

    fun update(id: Int, note: Note?): Boolean {
        // find the note object by the index number
        val foundNote = findNote(id)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        // if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveNote(id: Int): Boolean {
        val foundNote = findNote(id)
        if (( foundNote != null) && (!foundNote.isNoteArchived)
          //  && ( foundNote.checkNoteCompletionStatus())
        ){
            foundNote.isNoteArchived = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun listAllNotes() =
        if (notes.isEmpty()) "No notes stored"
        else formatListString(notes)

    fun listActiveNotes() =
        if (numberOfActiveNotes() == 0) "No active notes stored"
        else formatListString(notes.filter { note -> !note.isNoteArchived })

    fun listArchivedNotes() =
        if (numberOfArchivedNotes() == 0) "No archived notes stored"
        else formatListString(notes.filter { note -> note.isNoteArchived })

    // ----------------------------------------------
    //  COUNTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun numberOfNotes() = notes.size
    fun numberOfArchivedNotes(): Int = notes.count { note: Note -> note.isNoteArchived }
    fun numberOfActiveNotes(): Int = notes.count { note: Note -> !note.isNoteArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findNote(noteId : Int) =  notes.find{ note -> note.noteId == noteId }

    fun searchNotesByTitle(searchString: String) =
        formatListString(notes.filter { note -> note.noteTitle.contains(searchString, ignoreCase = true) })

}