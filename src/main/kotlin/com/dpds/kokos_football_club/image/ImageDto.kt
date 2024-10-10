package com.dpds.kokos_football_club.image

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.multipart.MultipartFile

data class SavedFile(
    @JsonProperty("id")
    val id: Long,
)

data class UploadImageRequest(
    val image: MultipartFile
)