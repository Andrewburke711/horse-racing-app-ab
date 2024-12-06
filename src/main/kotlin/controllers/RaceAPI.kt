package controllers

import ie.setu.models.Race
import utils.formatListString
import java.util.ArrayList

class RaceAPI() {

    private var races = ArrayList<Race>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR RACE ArrayList
    /**
     * Adds a new race to the list of races.
     *
     * @param race The race to add.
     * @return `true` if the race is successfully added, `false` otherwise.
     */
    // ----------------------------------------------
    fun add(race: Race): Boolean {
        race.raceId = getId()
        return races.add(race)
    }

    fun delete(id: Int) = races.removeIf { race -> race.raceId == id }

    fun update(id: Int, race: Race?): Boolean {
        // find the race object by the index number
        val foundRace = findRace(id)

        // if the race exists, use the race details passed as parameters to update the found race in the ArrayList.
        if ((foundRace != null) && (race != null)) {
            foundRace.raceTitle = race.raceTitle
            foundRace.raceDistance = race.raceDistance
            foundRace.raceClass = race.raceClass
            foundRace.raceStartTime = race.raceStartTime
            foundRace.raceTrackCondition = race.raceTrackCondition
            foundRace.isRaceFinished = race.isRaceFinished
            return true
        }

        // if the race was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveRace(id: Int): Boolean {
        val foundRace = findRace(id)
        if (( foundRace != null) && (!foundRace.isRaceFinished)
          //  && ( foundRace.checkRaceCompletionStatus())
        ){
            foundRace.isRaceFinished = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR RACE ArrayList
    // ----------------------------------------------
    fun listAllRaces() =
        if (races.isEmpty()) "No races stored"
        else formatListString(races)

    fun listFinishedRaces() =
        if (numberOfFinishedRaces() == 0) "No finished races stored"
        else formatListString(races.filter { race -> race.isRaceFinished })

    fun listFutureRaces() =
        if (numberOfFutureRaces() == 0) "No future races stored"
        else formatListString(races.filter { race -> !race.isRaceFinished })

    // ----------------------------------------------
    //  COUNTING METHODS FOR RACE ArrayList
    // ----------------------------------------------
    fun numberOfRaces() = races.size
    fun numberOfFinishedRaces(): Int = races.count { race: Race -> race.isRaceFinished }
    fun numberOfFutureRaces(): Int = races.count { race: Race -> !race.isRaceFinished }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findRace(raceId : Int) =  races.find{ race -> race.raceId == raceId }

    fun searchRacesByTitle(searchString: String) =
        formatListString(races.filter { race -> race.raceTitle.contains(searchString, ignoreCase = true) })

}