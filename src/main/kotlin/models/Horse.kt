package ie.setu.models

/**
 * Represents a horse in a race.
 *
 * @property horseId Unique identifier for the horse.
 * @property horseName The name of the horse.
 * @property horseSex The sex of the horse (e.g., Male/Female).
 * @property horseWeight The weight of the horse in kilograms.
 * @property horseJockey The name of the jockey riding the horse.
 * @property didHorseCompleteRace Whether the horse completed the race (`true`/`false`).
 */


data class Horse (var horseId: Int = 0,
                 var horseName : String,
                  var horseSex: String,
                  var horseWeight: Int,
                  var horseJockey: String,
                  var didHorseCompleteRace: Boolean,
)