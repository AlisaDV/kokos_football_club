package com.dpds.kokos_football_club.partner

import com.dpds.kokos_football_club.team.Team
import jakarta.persistence.*

@Entity
@Table(name = "partners")
class Partner (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    val title: String,
    val img: String,
    val donate: Int,
    @ManyToOne
    val team: Team
)