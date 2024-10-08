package com.dpds.kokos_football_club.purchase;

import com.dpds.kokos_football_club.product_cart.ProductCart
import com.dpds.kokos_football_club.user.User
import com.dpds.kokos_football_club.user.UserOrdering
import com.dpds.kokos_football_club.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class PurchaseService @Autowired constructor(
    private val purchaseRepository: PurchaseRepository,
    private val productCart: ProductCart,
    private val userService: UserService
){

    fun getPurchaseList(
        page: Int,
        pageSize: Int,
        ordering: PurchaseOrdering,
        search: String
    ): PageImpl<Purchase> {
        val purchases = purchaseRepository.findAll().filter {it.status.name.lowercase().contains(search.lowercase()) }

        val sort = when(ordering) {
            PurchaseOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            PurchaseOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            PurchaseOrdering.STATUS_ASC -> Sort.by(Sort.Direction.ASC, "status")
            PurchaseOrdering.STATUS_DESC -> Sort.by(Sort.Direction.DESC, "status")
            PurchaseOrdering.ORDERING_TIME_ASC -> Sort.by(Sort.Direction.ASC, "orderingTime")
            PurchaseOrdering.ORDERING_TIME_DESC -> Sort.by(Sort.Direction.DESC, "orderingTime")
            PurchaseOrdering.ARRIVAL_TIME_ASC -> Sort.by(Sort.Direction.ASC, "arrivalTime")
            PurchaseOrdering.ARRIVAL_TIME_DESC -> Sort.by(Sort.Direction.DESC, "arrivalTime")
        }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        return PageImpl(purchases.drop(pageSize * page).take(pageSize), pageRequest, purchases.size.toLong())
    }


    fun createPurchase(login: String): Purchase {
        val user = userService.getByLogin(login)
        val purchase =  Purchase(
            orderingTime = Calendar.getInstance(),
            arrivalTime = null,
            status = PurchaseStatus.IN_PROCESSING,
            user = user
        )
        purchase.products.addAll(productCart.products)
        user.purchases.add(purchase)
        productCart.products.clear()
        return purchaseRepository.save(purchase)
    }

}