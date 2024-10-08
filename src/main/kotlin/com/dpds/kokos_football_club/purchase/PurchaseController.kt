package com.dpds.kokos_football_club.purchase

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/purchase")
class PurchaseController @Autowired constructor(
    private val purchaseService: PurchaseService
){

    @Operation(
        summary = "Получить список заказов",
        tags = ["Заказы"]
    )
    @GetMapping("/")
    fun getPurchase(
        authenticatedPrincipal: UserDetails
    ): PurchaseResponse {
        val purchase = purchaseService.createPurchase(authenticatedPrincipal.username)
        return PurchaseResponse(purchase)
    }

    @Operation(
        summary = "Создать заказ",
        tags = ["Заказы"]
    )
    @PostMapping("/")
    fun createPurchase(
        authenticatedPrincipal: UserDetails
    ): PurchaseResponse {
        val purchase = purchaseService.createPurchase(authenticatedPrincipal.username)
        return PurchaseResponse(purchase)
    }
}