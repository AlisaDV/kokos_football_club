package com.dpds.kokos_football_club.match_result

import com.fasterxml.jackson.annotation.JsonProperty

data class MatchResultRequest(
    @JsonProperty("team_id")
    val teamId: Long,
)
