package com.dpds.kokos_football_club.product_cart

import com.dpds.kokos_football_club.product.Product
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductCartResponse(
    @JsonProperty("products")
    val products: MutableList<ProductsInCartResponse>
) {
    constructor(productCart: ProductCart): this(
        products = productCart.products.map { ProductsInCartResponse(it) }.toMutableList()
    )
}

data class ProductsInCartResponse(
    @JsonProperty("product_id")
    val id: Long,
    @JsonProperty("title")
    val title: String
) {
    constructor(product: Product): this(
        id = product.id,
        title = product.title
    )
}