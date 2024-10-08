package com.dpds.kokos_football_club.product_cart

import com.dpds.kokos_football_club.product.Product
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@Component
@SessionScope
class ProductCart(
    val products: MutableList<Product>
)