package ie.setu.models

data class Race(var raceId: Int = 0,
                var raceTitle: String,
                var raceDistance: Float,
                var raceClass: String,
                var raceStartTime: String,
                var raceTrackCondition: String,
                var isRaceFinished: Boolean,
    )
