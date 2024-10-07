package com.dpds.kokos_football_club.statistic

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class StatisticRequest(
    @JsonProperty("date")
    val date: Calendar,
    @JsonProperty("level_ball_control")
    val levelBallControl: String,
    @JsonProperty("number_of_goals")
    val numberOfGoals: Int,
    @JsonProperty("number_of_hits_to_frame")
    val numberOfHitsToFrame: String,
    @JsonProperty("number_of_points")
    val numberOfPoints: Int,
    @JsonProperty("number_of_passes")
    val numberOfPasses: Int
)

data class StatisticResponse(
    @JsonProperty("date")
    val date: Calendar,
    @JsonProperty("level_ball_control")
    val levelBallControl: String,
    @JsonProperty("number_of_goals")
    val numberOfGoals: Int,
    @JsonProperty("number_of_hits_to_frame")
    val numberOfHitsToFrame: String,
    @JsonProperty("number_of_points")
    val numberOfPoints: Int,
    @JsonProperty("number_of_passes")
    val numberOfPasses: Int
) {
    constructor(statistic: Statistic): this(
        date = statistic.date,
        levelBallControl = statistic.levelBallControl,
        numberOfGoals = statistic.numberOfGoals,
        numberOfHitsToFrame = statistic.numberOfHitsToFrame,
        numberOfPoints = statistic.numberOfPoints,
        numberOfPasses = statistic.numberOfPasses
    )
}
