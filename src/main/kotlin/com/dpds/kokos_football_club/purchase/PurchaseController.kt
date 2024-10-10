package com.dpds.kokos_football_club.purchase

import com.dpds.kokos_football_club.user.UserOrdering
import com.dpds.kokos_football_club.util.DetailsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


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
    fun getPurchaseList(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: PurchaseOrdering
    ): PageImpl<PurchaseResponse> {
        val purchase = purchaseService.getPurchaseList(page, pageSize, ordering, search)
        val response: MutableList<PurchaseResponse> = mutableListOf()
        purchase.forEach { response.add(PurchaseResponse(it)) }
        return PageImpl(response)
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

    @Operation(
        summary = "Изменить статус заказа",
        tags = ["Заказы"]
    )
    @PatchMapping("/{id}/change_status/")
    fun changeStatus(
        @Parameter(name = "ID заказа") @PathVariable("id") id: Long,
        @RequestBody changePurchaseStatusRequest: ChangePurchaseStatusRequest
    ): DetailsResponse {
        purchaseService.changeStatus(id, changePurchaseStatusRequest)
        return DetailsResponse("Статус успешно изменён")
    }
}