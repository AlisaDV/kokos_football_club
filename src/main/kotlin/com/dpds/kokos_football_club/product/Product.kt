package com.dpds.kokos_football_club.product

import com.dpds.kokos_football_club.purchase.Purchase
import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1L,
    var title: String,
    var description: String,
    var price: Int,
    var img: String,
    @ManyToOne
    val purchase: Purchase?
)