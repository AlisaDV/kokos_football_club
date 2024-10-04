package com.dpds.kokos_football_club.user

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    val login: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var age: Int,
    var isBlocked: Boolean = false,
    //TODO var img: String
    @Enumerated(EnumType.STRING)
    var role: UserRole
)