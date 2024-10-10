package com.dpds.kokos_football_club.image

import com.dpds.kokos_football_club.partner.Partner
import com.dpds.kokos_football_club.player.Player
import com.dpds.kokos_football_club.product.Product
import com.dpds.kokos_football_club.publication.Publication
import com.dpds.kokos_football_club.team.Team
import com.dpds.kokos_football_club.user.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "images",
)
class Image (
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = -1L,
    val fullFilePath: String,
    val previewFilePath: String,
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Calendar = Calendar.getInstance(),
    @ManyToOne
    val createdBy: User?,
    @OneToOne(mappedBy = "avatar")
    val user: User? = null,
    @OneToOne(mappedBy = "logo")
    val team: Team? = null,
    @OneToOne(mappedBy = "img")
    val publication: Publication? = null,
    @OneToOne(mappedBy = "img")
    val product: Product? = null,
    @OneToOne(mappedBy = "img")
    val player: Player? = null,
    @OneToOne(mappedBy = "img")
    val partner: Partner? = null,
) {
    fun isCreatedByUser(username: String?): Boolean {
        return createdBy?.login == username
    }

    fun isUserCanView(user: User): Boolean {
        return true
    }
}