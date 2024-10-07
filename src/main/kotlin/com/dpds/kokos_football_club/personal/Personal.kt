package com.dpds.kokos_football_club.personal

import com.dpds.kokos_football_club.team.Team
import jakarta.persistence.*

@Entity
@Table(name = "personals")
class Personal (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1L,
    val firstName: String,
    val lastName: String,
    val age: Int,
    @Enumerated(EnumType.STRING)
    val type: PersonalType,
    @ManyToOne
    val team: Team
)