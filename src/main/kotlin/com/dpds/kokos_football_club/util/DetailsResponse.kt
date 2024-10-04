package com.dpds.kokos_football_club.util

import com.fasterxml.jackson.annotation.JsonProperty

data class DetailsResponse(
    @JsonProperty("detail")
    val details: String
)
