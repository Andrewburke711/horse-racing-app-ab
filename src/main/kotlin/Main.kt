import controllers.RaceAPI
import ie.setu.models.Race
import utils.readNextBoolean
import utils.readNextFloat
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val raceAPI = RaceAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addRace()
            2 -> listRaces()
            3 -> updateRace()
            4 -> deleteRace()
           // 5 -> archiveRace()
            10 -> searchRaces()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}


fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  RACE KEEPER APP                  |
         > -----------------------------------------------------  
         > | HORSE MENU                                         | 
         > |   1) Add a race                                    |
         > |   2) List races                                    |
         > |   3) Update a race                                 |
         > |   4) Delete a race                                 |
         > -----------------------------------------------------  
         > | REPORT MENU FOR HORSES                             | 
         > |   10) Search for all horses (by race title)        |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
//RACE MENU
//------------------------------------
fun addRace() {
    val raceTitle = readNextLine("Enter a title for the race: ")
    val raceDistance = readNextFloat("Enter a distance (Miles): ")
    val raceClass = readNextLine("Enter a class for the race: ")
    val raceStartTime = readNextLine("Enter a time for the race start: ")
    val raceTrackCondition = readNextLine("Enter track condition: ")
    val isRaceFinished = readNextBoolean("Is race finished (True/False): ")
    val isAdded = raceAPI.add(Race(raceTitle = raceTitle, raceDistance = raceDistance, raceClass = raceClass, raceStartTime =
    raceStartTime, raceTrackCondition = raceTrackCondition, isRaceFinished = isRaceFinished  ))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listRaces() {
    if (raceAPI.numberOfRaces() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL races           |
                  > |   2) View FINISHED races      |
                  > |   3) View FUTURE races        |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllRaces()
            2 -> listFinishedRaces()
            3 -> listFutureRaces()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No horses stored")
    }
}

fun listAllRaces() = println(raceAPI.listAllRaces())
fun listFinishedRaces() = println(raceAPI.listFinishedRaces())
fun listFutureRaces() = println(raceAPI.listFutureRaces())

fun updateRace() {
    listRaces()
    if (raceAPI.numberOfRaces() > 0) {
        // only ask the user to choose the horse if horses exist
        val id = readNextInt("Enter the id of the horse to update: ")
        if (raceAPI.findRace(id) != null) {
            val raceTitle = readNextLine("Enter a title for the race: ")
            val raceDistance = readNextFloat("Enter a distance (Miles): ")
            val raceClass = readNextLine("Enter a class for the race: ")
            val raceStartTime = readNextLine("Enter a time for the race start: ")
            val raceTrackCondition = readNextLine("Enter a time for the race start: ")
            val isRaceFinished = readNextBoolean("Is race finished (True/False): ")

            // pass the index of the horse and the new horse details to RaceAPI for updating and check for success.
            if (raceAPI.update(id, Race(0, raceTitle, raceDistance, raceClass, raceStartTime, raceTrackCondition, isRaceFinished))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no horses for this index number")
        }
    }
}

fun deleteRace() {
    listRaces()
    if (raceAPI.numberOfRaces() > 0) {
        // only ask the user to choose the horse to delete if horses exist
        val id = readNextInt("Enter the id of the horse to delete: ")
        // pass the index of the horse to RaceAPI for deleting and check for success.
        val horseToDelete = raceAPI.delete(id)
        if (horseToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

//fun archiveHorse() {
//    listActiveHorses()
//    if (raceAPI.numberOfActiveHorses() > 0) {
//        // only ask the user to choose the horse to archive if active horses exist
//        val id = readNextInt("Enter the id of the horse to archive: ")
//        // pass the index of the horse to RaceAPI for archiving and check for success.
//        if (raceAPI.archiveHorse(id)) {
//            println("Archive Successful!")
//        } else {
//            println("Archive NOT Successful")
//        }
//    }
//}

//------------------------------------
//HORSE REPORTS MENU
//------------------------------------
fun searchRaces() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = raceAPI.searchRacesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No horses found")
    } else {
        println(searchResults)
    }
}

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

//private fun askUserToChooseActiveHorse(): Race? {
//    listActiveHorses()
//    if (raceAPI.numberOfActiveHorses() > 0) {
//        val horse = raceAPI.findHorse(readNextInt("\nEnter the id of the horse: "))
//        if (horse != null) {
//            if (horse.isHorseArchived) {
//                println("Horse is NOT Active, it is Archived")
//            } else {
//                return horse //chosen horse is active
//            }
//        } else {
//            println("Horse id is not valid")
//        }
//    }
//    return null //selected horse is not active
//}
