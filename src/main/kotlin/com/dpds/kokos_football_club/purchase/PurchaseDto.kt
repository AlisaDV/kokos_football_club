package com.dpds.kokos_football_club.purchase

import com.dpds.kokos_football_club.product.Product
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class PurchaseResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("ordering_time")
    val orderingTime: Calendar,
    @JsonProperty("arrival_time")
    val arrivalTime: Calendar?,
    @JsonProperty("status")
    val status: PurchaseStatus,
    @JsonProperty("products")
    val products: List<PurchaseProductsResponse>
) {
    constructor(purchase: Purchase): this (
        id = purchase.id,
        orderingTime = purchase.orderingTime,
        arrivalTime = purchase.arrivalTime,
        status = purchase.status,
        products = purchase.products.map { PurchaseProductsResponse(it) }
    )
}

data class PurchaseProductsResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String
) {
    constructor(product: Product): this (
        id = product.id,
        title = product.title
    )
}