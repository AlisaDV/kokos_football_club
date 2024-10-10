package com.dpds.kokos_football_club.team

import com.dpds.kokos_football_club.ex_player.ExPlayer
import com.dpds.kokos_football_club.image.Image
import com.dpds.kokos_football_club.partner.Partner
import com.dpds.kokos_football_club.personal.Personal
import com.dpds.kokos_football_club.player.Player
import com.dpds.kokos_football_club.statistic.Statistic
import jakarta.persistence.*


@Entity
@Table(name = "teams")
class Team (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    var title: String,
    @OneToOne
    @JoinColumn(
        name = "logo_id"
    )
    var logo: Image? = null,
    var description: String,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "team")
    val players: MutableList<Player> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "team")
    val exPlayers: MutableList<ExPlayer> = mutableListOf(),
    @OneToOne
    var statistic: Statistic?,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "team")
    var personal: MutableList<Personal> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "team")
    val partners: MutableList<Partner> = mutableListOf()
)