package com.dpds.kokos_football_club.player;

import com.dpds.kokos_football_club.ex_player.ExPlayer
import com.dpds.kokos_football_club.ex_player.ExPlayerRepository
import com.dpds.kokos_football_club.ex_team.ExTeam
import com.dpds.kokos_football_club.ex_team.ExTeamRepository
import com.dpds.kokos_football_club.exception.DetailsException
import com.dpds.kokos_football_club.exception.NotFoundException
import com.dpds.kokos_football_club.team.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlayerService @Autowired constructor(
    private val playerRepository: PlayerRepository,
    private val teamService: TeamService,
    private val exTeamRepository: ExTeamRepository,
    private val exPlayerRepository: ExPlayerRepository
){


    fun getPlayerList(
        page: Int,
        pageSize: Int,
        ordering: PlayerOrdering,
        search: String
    ): PageImpl<Player> {
        val players = playerRepository.findAll().filter {
                it.firstName.lowercase().contains(search.lowercase())
                || it.lastName.lowercase().contains(search.lowercase())
        }

        val sort = when(ordering) {
            PlayerOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            PlayerOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            PlayerOrdering.TEAM_ASC -> Sort.by(Sort.Direction.ASC, "team")
            PlayerOrdering.TEAM_DESC -> Sort.by(Sort.Direction.DESC, "team")
        }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        return PageImpl(players.drop(pageSize * page).take(pageSize), pageRequest, players.size.toLong())
    }

    fun getPlayer(id: Long): Player {
        return playerRepository.findByIdOrNull(id) ?: throw NotFoundException("Пользователь не найден")
    }

    fun createPlayer(playerRequest: PlayerRequest): Player {

        return playerRepository.save(
            Player(
                firstName = playerRequest.firstName,
                lastName = playerRequest.lastName,
                age = playerRequest.age,
                team = playerRequest.teamId?.let { teamService.getTeam(it) },
                img = playerRequest.img
            )
        )
    }

    fun updatePlayer(id: Long, playerRequest: PlayerRequest): Player {
        val player = getPlayer(id)
        player.firstName = playerRequest.firstName
        player.lastName = playerRequest.lastName
        player.age = playerRequest.age
        if(playerRequest.teamId != null){
            val exTeam = player.team?.let {
                ExTeam(
                    team = it,
                    player = player
                )
            }
            if (exTeam != null) {
                player.exTeams.add(exTeam)
            }
            player.team = teamService.getTeam(playerRequest.teamId)
        }
        return playerRepository.save(player)
    }

    fun deletePlayer(id: Long) {
        playerRepository.deleteById(id)
    }

    fun addPlayerToTeam(playerId: Long, addToTeamRequest: AddToTeamRequest) {
        val player = getPlayer(playerId)
        val team = teamService.getTeam(addToTeamRequest.teamId)
        player.team = team
        team.players.add(player)
        playerRepository.save(player)
        teamService.saveTeam(team)
    }

    fun removePlayerFromTeam(playerId: Long) {
        val player = getPlayer(playerId)
        if(player.team == null) {
            throw DetailsException("Игрок не состоит в команде")
        }
        val team = player.team
        val exPlayer = team?.let {
            ExPlayer(
                player = player,
                team = it
            )
        }
        if (exPlayer != null) {
            team.exPlayers.add(exPlayer)
            exPlayerRepository.save(exPlayer)
        }
        val exTeam = team?.let {
            ExTeam(
                team = it,
                player = player
            )
        }
        if (exTeam != null) {
            player.exTeams.add(exTeam)
            exTeamRepository.save(exTeam)
        }
        playerRepository.save(player)
        if (team != null) {
            teamService.saveTeam(team)
        }

    }

}