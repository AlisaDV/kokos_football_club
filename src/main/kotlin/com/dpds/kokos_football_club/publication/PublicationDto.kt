package com.dpds.kokos_football_club.publication

import com.dpds.kokos_football_club.image.UploadImageRequest
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
    val img: UploadImageRequest?,
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
    val imgId: Long?,
) {
    constructor(publication: Publication): this (
        id = publication.id,
        title = publication.title,
        description = publication.description,
        datePublication = publication.datePublication,
        imgId = publication.img?.id
    )
}

data class UpdatePublicationRequest(
    @JsonProperty("title")
    @Size(max = 200)
    val title: String,
    @JsonProperty("description")
    val description: String,
)
