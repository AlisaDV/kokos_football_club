package com.dpds.kokos_football_club.player

import com.dpds.kokos_football_club.ex_team.ExTeam
import com.dpds.kokos_football_club.team.Team
import com.fasterxml.jackson.annotation.JsonProperty

data class PlayerRequest(
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    @JsonProperty("age")
    val age: Int,
    @JsonProperty("image")
    val img: String,
    @JsonProperty("team_id")
    val teamId: Long?
)

data class PlayerResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("full_name")
    val fullName: String,
    @JsonProperty("age")
    val age: Int,
    @JsonProperty("image")
    val img: String,
    @JsonProperty("team")
    val team: Team?,
    @JsonProperty("ex_teams")
    val exTeams: MutableList<PlayerExTeamsResponse>
) {
    constructor(player: Player): this (
        id = player.id,
        fullName = player.firstName + " " + player.lastName,
        age = player.age,
        img = player.img,
        team = player.team,
        exTeams = player.exTeams.map { PlayerExTeamsResponse(it) }.toMutableList()
    )
}

data class AddToTeamRequest(
    @JsonProperty("team_id")
    val teamId: Long
)

data class PlayerExTeamsResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String
) {
    constructor(exTeam: ExTeam): this(
        id = exTeam.id,
        title = exTeam.team.title
    )
}
