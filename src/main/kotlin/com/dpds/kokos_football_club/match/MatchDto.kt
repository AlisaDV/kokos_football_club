package com.dpds.kokos_football_club.match

import com.dpds.kokos_football_club.team.Team
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import java.util.*

data class MatchRequest(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("date")
    val date: Calendar,
    @JsonProperty("teams")
    val teams: MutableList<MatchTeamDto> = mutableListOf()
)

data class MatchTeamDto(
    @JsonProperty("team_id")
    val teamId: Long
)

data class MatchResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("date")
    val date: Calendar,
    @JsonProperty("teams")
    val teams: MutableList<MatchTeamsResponse> = mutableListOf()
) {
    constructor(match: Match): this(
        id = match.id,
        title = match.title,
        date = match.date,
        teams = match.teams.map { MatchTeamsResponse(it) }.toMutableList()
    )
}

data class MatchTeamsResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String
) {
    constructor(team: Team): this(
        id = team.id,
        title = team.title
    )
}