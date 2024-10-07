package com.dpds.kokos_football_club.player

import com.dpds.kokos_football_club.ex_team.ExTeam
import com.dpds.kokos_football_club.team.Team
import jakarta.persistence.*


@Entity
@Table(name = "players")
class Player (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    var firstName: String,
    var lastName: String,
    var age: Int,
    val img: String,
    @ManyToOne
    @OrderBy("title ASC")
    var team: Team?,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "player")
    val exTeams: MutableList<ExTeam> = mutableListOf()
)