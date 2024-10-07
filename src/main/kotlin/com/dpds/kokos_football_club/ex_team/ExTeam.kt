package com.dpds.kokos_football_club.ex_team

import com.dpds.kokos_football_club.player.Player
import com.dpds.kokos_football_club.team.Team
import jakarta.persistence.*

@Entity
@Table(name = "ex_teams")
class ExTeam (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1L,
    @OneToOne
    val team: Team,
    @ManyToOne
    val player: Player
)