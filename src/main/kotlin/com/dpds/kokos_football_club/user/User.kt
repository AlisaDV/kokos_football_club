package com.dpds.kokos_football_club.user

import com.dpds.kokos_football_club.purchase.Purchase
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    @Column(unique = true)
    val login: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var age: Int,
    var isBlocked: Boolean = false,
    //TODO var img: String
    @Enumerated(EnumType.STRING)
    var role: UserRole,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var purchases: MutableList<Purchase> = mutableListOf()
)