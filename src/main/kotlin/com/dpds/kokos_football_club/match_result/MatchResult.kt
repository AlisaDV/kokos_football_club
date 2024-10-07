package com.dpds.kokos_football_club.match_result

import com.dpds.kokos_football_club.match.Match
import com.dpds.kokos_football_club.team.Team
import jakarta.persistence.*

@Entity
@Table(name = "match_results")
class MatchResult (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1L,
    @OneToOne
    val team: Team,
    @OneToOne(fetch = FetchType.LAZY)
    val match: Match
)