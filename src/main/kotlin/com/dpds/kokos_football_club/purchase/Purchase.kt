package com.dpds.kokos_football_club.purchase

import com.dpds.kokos_football_club.product.Product
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "purchases")
class Purchase (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    @Temporal(TemporalType.TIMESTAMP)
    val orderingTime: Calendar,
    @Temporal(TemporalType.TIMESTAMP)
    val arrivalTime: Calendar,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "purchase")
    val products: MutableList<Product> = mutableListOf(),
    @Enumerated(EnumType.STRING)
    val status: PurchaseStatus
)