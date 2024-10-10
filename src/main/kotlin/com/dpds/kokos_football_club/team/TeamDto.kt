package com.dpds.kokos_football_club.team

import com.dpds.kokos_football_club.ex_player.ExPlayer
import com.dpds.kokos_football_club.player.Player
import com.dpds.kokos_football_club.product.Product
import com.dpds.kokos_football_club.statistic.Statistic
import com.dpds.kokos_football_club.statistic.StatisticResponse
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Size
import java.util.*

data class TeamRequest(
    @JsonProperty("title")
    @field:Size(max = 200)
    val title: String,
    @JsonProperty("img")
    @field:Size(max = 200)
    val img: String,
    @JsonProperty("description")
    @field:Size(max = 200)
    val description: String,
)

data class UpdateTeamRequest(
    @JsonProperty("title")
    @field:Size(max = 200)
    val title: String,
    @JsonProperty("img")
    @field:Size(max = 200)
    val img: String,
    @JsonProperty("description")
    @field:Size(max = 200)
    val description: String,
)

data class TeamPlayersRequest(
    @JsonProperty("player_id")
    val userId: Long
)

data class TeamExPlayersRequest(
    @JsonProperty("ex_player_id")
    val exPlayerId: Long
)


data class TeamResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("logo")
    val logoId: Long?,
    @JsonProperty("description")
    val description: String,
) {
    constructor(team: Team): this(
        id = team.id,
        title = team.title,
        logoId = team.logo?.id,
        description = team.description,
    )
}

data class TeamProfileResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("logo")
    val logoId: Long?,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("players")
    val players: List<Player>,
    @JsonProperty("ex_players")
    val exPlayers: List<ExPlayer>,
    @JsonProperty("statistic")
    val statistic: StatisticResponse?
) {
    constructor(team: Team): this(
        id = team.id,
        title = team.title,
        logoId = team.logo?.id,
        description = team.description,
        players = team.players.toList(),
        exPlayers = team.exPlayers.toList(),
        statistic = team.statistic?.let { StatisticResponse(it) }
    )
}
