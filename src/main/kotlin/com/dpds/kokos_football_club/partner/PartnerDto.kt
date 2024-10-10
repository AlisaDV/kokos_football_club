package com.dpds.kokos_football_club.partner

import com.dpds.kokos_football_club.image.UploadImageRequest
import com.fasterxml.jackson.annotation.JsonProperty

data class PartnerRequest(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("img")
    val img: UploadImageRequest,
    @JsonProperty("donate")
    val donate: Int,
)

data class RemovePartnerRequest(
    @JsonProperty("partner_id")
    val partnerId: Long
)
