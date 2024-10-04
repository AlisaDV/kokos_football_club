package com.dpds.kokos_football_club.publication

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.util.*

data class PublicationRequest(
    @JsonProperty("title")
    @Size(max = 200)
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("date_publication")
    val datePublication: Calendar,
    @JsonProperty("image")
    @Size(max = 200)
    val img: String?,
    @JsonProperty("type")
    val type: PublicationType
)

data class PublicationResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("date_publication")
    val datePublication: Calendar,
    @JsonProperty("image")
    val img: String?,
) {
    constructor(publication: Publication): this (
        id = publication.id,
        title = publication.title,
        description = publication.description,
        datePublication = publication.datePublication,
        img = publication.img
    )
}

data class UpdatePublicationRequest(
    @JsonProperty("title")
    @Size(max = 200)
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("image")
    @Size(max = 200)
    val img: String?,
)
