package com.dpds.kokos_football_club.statistic

import jakarta.persistence.*
import java.util.Calendar

@Entity
@Table(name = "statistics")
class Statistic (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    val date: Calendar,
    val levelBallControl: String,
    val numberOfGoals: Int,
    val numberOfHitsToFrame: String,
    val numberOfPoints: Int,
    val numberOfPasses: Int
)