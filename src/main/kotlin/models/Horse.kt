package ie.setu.models

data class Horse (var horseId: Int = 0,
                 var horseName : String,
                  var horseSex: String,
                  var horseWeight: Int,
                  var horseJockey: String,
                  var didHorseCompleteRace: Boolean,
)