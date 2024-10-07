package com.dpds.kokos_football_club.ex_player

import com.dpds.kokos_football_club.player.Player
import com.dpds.kokos_football_club.team.Team
import jakarta.persistence.*


@Entity
@Table(name = "ex_players")
class ExPlayer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = -1L,
    @OneToOne(fetch = FetchType.LAZY)
    val player: Player,
    @ManyToOne
    val team: Team
)