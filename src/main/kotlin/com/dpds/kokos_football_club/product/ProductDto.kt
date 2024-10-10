package com.dpds.kokos_football_club.product

import com.dpds.kokos_football_club.image.UploadImageRequest
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductRequest(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("price")
    val price: Int,
    @JsonProperty("image")
    val img: UploadImageRequest?,
)

data class ProductResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("price")
    val price: Int,
    @JsonProperty("image")
    val imgId: Long?,
) {
    constructor(product: Product): this(
        id = product.id,
        title = product.title,
        description = product.description,
        price = product.price,
        imgId = product.img?.id
    )
}