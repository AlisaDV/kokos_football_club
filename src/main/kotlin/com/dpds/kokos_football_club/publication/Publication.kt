package com.dpds.kokos_football_club.publication

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "publications")
class Publication (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    var title: String,
    @Column(columnDefinition = "TEXT")
    var description: String,
    @Temporal(TemporalType.DATE)
    val datePublication: Calendar,
    var img: String?,
    @Enumerated(EnumType.STRING)
    val type: PublicationType
)