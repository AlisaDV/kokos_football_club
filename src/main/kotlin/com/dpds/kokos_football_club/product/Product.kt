package com.dpds.kokos_football_club.product

import com.dpds.kokos_football_club.image.Image
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
    @OneToOne
    @JoinColumn(
        name = "avatar_id"
    )
    var img: Image? = null,
    @ManyToOne
    val purchase: Purchase?
)