package com.dpds.kokos_football_club.product_cart

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cart")
class ProductCartController @Autowired constructor(
    private val productCart: ProductCart
) {
    @Operation(
        summary = "Получить корзину",
        tags = ["Корзина"]
    )
    @GetMapping("/")
    fun getCart(
    ): ProductCartResponse {
        return ProductCartResponse(productCart)
    }
}