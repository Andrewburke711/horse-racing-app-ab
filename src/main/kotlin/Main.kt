import controllers.RaceAPI
import ie.setu.models.Horse
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
            5 -> addHorseToRace()
            6 -> updateHorseContentsInRace()

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
         > |   5) Add Horse to race                             |
         > |   6) Update Horse Contents in Race                 |
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

//Adding Horses To Menu
private fun addHorseToRace() {
    // Ask the user to choose an active race
    val race: Race? = askUserToChooseActiveRace()
    if (race != null) {

        // Prompt for horse details
        val horseName = readNextLine("Enter the horse's name: ")
        val horseSex = readNextLine("Enter the horse's sex (e.g., Male/Female): ")
        val horseWeight = readNextInt("Enter the horse's weight (in kg): ")
        val horseJockey = readNextLine("Enter the jockey's name: ")
        val didHorseCompleteRace = readNextBoolean("Did the horse complete the race? (True/False): ")

        // Create a new Horse object
        val newHorse = Horse(
            horseName = horseName,
            horseSex = horseSex,
            horseWeight = horseWeight,
            horseJockey = horseJockey,
            didHorseCompleteRace = didHorseCompleteRace
        )

        // Attempt to add the horse to the race
        if (race.addHorse(newHorse)) {
            println("Horse added successfully!")
        } else {
            println("Failed to add horse. It might already exist.")
        }
    }
}


fun updateHorseContentsInRace() {
    val race: Race? = askUserToChooseActiveRace()
    if (race != null) {
        val horse: Horse? = askUserToChooseHorse(race)
        if (horse != null) {
            val horseName = readNextLine("Enter new horse name (or press Enter to keep '${horse.horseName}'): ")
            val horseSex = readNextLine("Enter new horse sex (or press Enter to keep '${horse.horseSex}'): ")
            val horseWeight = readNextInt("Enter new horse weight (or press 0 to keep '${horse.horseWeight}'): ")
            val horseJockey = readNextLine("Enter new jockey name (or press Enter to keep '${horse.horseJockey}'): ")
            val didHorseCompleteRace = readNextBoolean("Did the horse complete the race? (True/False): ")

            // Create a new Horse object with updated details
            val updatedHorse = Horse(
                horseId = horse.horseId,
                horseName = if (horseName.isNotEmpty()) horseName else horse.horseName,
                horseSex = if (horseSex.isNotEmpty()) horseSex else horse.horseSex,
                horseWeight = if (horseWeight != 0) horseWeight else horse.horseWeight,
                horseJockey = if (horseJockey.isNotEmpty()) horseJockey else horse.horseJockey,
                didHorseCompleteRace = didHorseCompleteRace
            )

            if (race.update(horse.horseId, updatedHorse)) {
                println("Horse updated successfully!")
            } else {
                println("Horse update failed.")
            }
        } else {
            println("Invalid Horse ID")
        }
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


private fun askUserToChooseHorse(race: Race): Horse? {
    if (race.numberOfHorses() > 0) {
        print(race.listHorses())
        return race.findOne(readNextInt("\nEnter the id of the horse: "))
    }
    else{
        println ("No horses for chosen race")
        return null
    }
}




private fun askUserToChooseActiveRace(): Race? {
    listAllRaces()
    if (raceAPI.numberOfRaces() > 0) {
        val race = raceAPI.findRace(readNextInt("\nEnter the id of the horse: "))
        if (race != null) {
            if (race.isRaceFinished) {
                println("Race is NOT Active, it is Archived")
            } else {
                return race //chosen horse is active
            }
        } else {
            println("Horse id is not valid")
        }
    }
    return null //selected horse is not active
}
