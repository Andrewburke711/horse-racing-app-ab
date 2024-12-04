import controllers.NoteAPI
import ie.setu.models.Note
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val noteAPI = NoteAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addNote()
            2 -> listNotes()
            3 -> updateNote()
            4 -> deleteNote()
            5 -> archiveNote()
            //6 -> addItemToNote()
            //7 -> updateItemContentsInNote()
            //8 -> deleteAnItem()
            //9 -> markItemStatus()
            10 -> searchNotes()
            //15 -> searchItems()
            //16 -> listToDoItems()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  NOTE KEEPER APP                  |
         > -----------------------------------------------------  
         > | NOTE MENU                                         |
         > |   1) Add a note                                   |
         > |   2) List notes                                   |
         > |   3) Update a note                                |
         > |   4) Delete a note                                |
         > |   5) Archive a note                               |
         > -----------------------------------------------------  
         > | ITEM MENU                                         | 
         > |   6) Add item to a note                           |
         > |   7) Update item contents on a note               |
         > |   8) Delete item from a note                      |
         > |   9) Mark item as complete/todo                   | 
         > -----------------------------------------------------  
         > | REPORT MENU FOR NOTES                             | 
         > |   10) Search for all notes (by note title)        |
         > |   11) .....                                       |
         > |   12) .....                                       |
         > |   13) .....                                       |
         > |   14) .....                                       |
         > -----------------------------------------------------  
         > | REPORT MENU FOR ITEMS                             |                                
         > |   15) Search for all items (by item description)  |
         > |   16) List TODO Items                             |
         > |   17) .....                                       |
         > |   18) .....                                       |
         > |   19) .....                                       |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
//NOTE MENU
//------------------------------------
fun addNote() {
    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle = noteTitle, notePriority = notePriority, noteCategory = noteCategory))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listNotes() {
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllNotes()
            2 -> listActiveNotes()
            3 -> listArchivedNotes()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllNotes() = println(noteAPI.listAllNotes())
fun listActiveNotes() = println(noteAPI.listActiveNotes())
fun listArchivedNotes() = println(noteAPI.listArchivedNotes())

fun updateNote() {
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the note to update: ")
        if (noteAPI.findNote(id) != null) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readNextLine("Enter a category for the note: ")

            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.update(id, Note(0, noteTitle, notePriority, noteCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteNote() {
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val id = readNextInt("Enter the id of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.delete(id)
        if (noteToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveNote() {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        // only ask the user to choose the note to archive if active notes exist
        val id = readNextInt("Enter the id of the note to archive: ")
        // pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveNote(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

//-------------------------------------------
//ITEM MENU (only available for active notes)
//-------------------------------------------

//TODO

//------------------------------------
//NOTE REPORTS MENU
//------------------------------------
fun searchNotes() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = noteAPI.searchNotesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
//ITEM REPORTS MENU
//------------------------------------

//TODO

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------

private fun askUserToChooseActiveNote(): Note? {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        val note = noteAPI.findNote(readNextInt("\nEnter the id of the note: "))
        if (note != null) {
            if (note.isNoteArchived) {
                println("Note is NOT Active, it is Archived")
            } else {
                return note //chosen note is active
            }
        } else {
            println("Note id is not valid")
        }
    }
    return null //selected note is not active
}
