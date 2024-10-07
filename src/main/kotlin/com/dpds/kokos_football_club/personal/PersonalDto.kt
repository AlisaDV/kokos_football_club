package com.dpds.kokos_football_club.personal

import com.fasterxml.jackson.annotation.JsonProperty

data class PersonalRequest(
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    @JsonProperty("age")
    val age: Int,
    @JsonProperty("type")
    val type: PersonalType,
    @JsonProperty("team_id")
    val teamId: Long
)

data class RemovePersonalRequest(
    @JsonProperty("personal_id")
    val personalId: Long
)
