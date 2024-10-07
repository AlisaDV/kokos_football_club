package com.dpds.kokos_football_club.match

import com.dpds.kokos_football_club.match_result.MatchResult
import com.dpds.kokos_football_club.team.Team
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "matches")
class Match (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1L,
    var title: String,
    @Temporal(TemporalType.TIMESTAMP)
    var date: Calendar,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "match_teams",
        joinColumns = [JoinColumn(name = "match_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "team_id", referencedColumnName = "id")]
    )
    var teams: MutableList<Team> = mutableListOf(),
    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "match", fetch = FetchType.LAZY)
    var matchResult: MatchResult?
)