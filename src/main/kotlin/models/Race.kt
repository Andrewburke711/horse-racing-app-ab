package ie.setu.models

import utils.formatSetString


data class Race(var raceId: Int = 0,
                var raceTitle: String,
                var raceDistance: Float,
                var raceClass: String,
                var raceStartTime: String,
                var raceTrackCondition: String,
                var isRaceFinished: Boolean,
                var horses : MutableSet<Horse> = mutableSetOf()) {

                    private var lastHorseId = 0
                    private fun getNextHorseId() = lastHorseId++

                    fun addHorse(horse: Horse) : Boolean {
                        horse.horseId = getNextHorseId()
                        return horses.add(horse)

                    }
                    fun numberOfHorses() = horses.size

                    fun findOne(id: Int): Horse?{
                        return horses.find{ horse -> horse.horseId == id }
                    }

                    fun delete(id: Int): Boolean {
                        return horses.removeIf { horse -> horse.horseId == id}
                    }

                    fun update(id: Int, newHorse: Horse): Boolean {
                        val foundHorse = findOne(id)

                        // If the object exists, update its properties with the details passed in the newHorse parameter
                        if (foundHorse != null) {
                            foundHorse.horseName = newHorse.horseName
                            foundHorse.horseSex = newHorse.horseSex
                            foundHorse.horseWeight = newHorse.horseWeight
                            foundHorse.horseJockey = newHorse.horseJockey
                            foundHorse.didHorseCompleteRace = newHorse.didHorseCompleteRace
                            return true
                        }

                        // If the object was not found, return false, indicating that the update was not successful
                        return false
                    }


    fun listHorses() =
                        if (horses.isEmpty())  "\tNO ITEMS ADDED"
                        else  formatSetString(horses)

                    override fun toString(): String {
                        val archived = if (isRaceFinished) 'Y' else 'N'
                        return "$raceId: $raceTitle, Distance($raceDistance), Class($raceClass), Start Time($raceStartTime) \n${listHorses()}"
                    }

                    fun checkHorseCompletionStatus(): Boolean {
                        if (horses.isNotEmpty()) {
                            for (horse in horses) {
                                if (!horse.didHorseCompleteRace) { // Fixed incorrect property access
                                    return false
                                }
                            }
                        }
                        return true // A race with no horses can be archived, or all horses have completed the race
                    }


}

