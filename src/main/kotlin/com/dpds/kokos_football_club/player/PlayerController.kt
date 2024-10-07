package com.dpds.kokos_football_club.player

import com.dpds.kokos_football_club.user.UserOrdering
import com.dpds.kokos_football_club.user.UserResponse
import com.dpds.kokos_football_club.util.DetailsResponse
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/player")
class PlayerController(
    private val playerRepository: PlayerRepository,
    private val playerService: PlayerService,
    private val objectMapper: ObjectMapper
) {

    @Operation(
        summary = "Получить список игроков",
        tags = ["Игроки"]
    )
    @GetMapping("/")
    fun getPlayerList(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: PlayerOrdering
    ): PageImpl<PlayerResponse> {
        val player = playerService.getPlayerList(page, pageSize, ordering, search)
        val playerResponse: MutableList<PlayerResponse> = mutableListOf()
        player.forEach {
            playerResponse.add(PlayerResponse(it))
        }
        return PageImpl(playerResponse)
    }

    @Operation(
        summary = "Получить игрока",
        tags = ["Игроки"]
    )
    @GetMapping("/{id}/")
    fun getPlayer(
       @Parameter(description = "ID игрока") @PathVariable("id") id: Long
    ): PlayerResponse {
        val player = playerService.getPlayer(id)
        return PlayerResponse(player)
    }

    @Operation(
        summary = "Создать игрока",
        tags = ["Игроки"]
    )
    @PostMapping("/")
    fun createPlayer(@RequestBody playerRequest: PlayerRequest): PlayerResponse {
        val player = playerService.createPlayer(playerRequest)
        return PlayerResponse(player)
    }

    @Operation(
        summary = "Обновить игрока",
        tags = ["Игроки"]
    )
    @PutMapping("/{id}/")
    fun updatePlayer(
        @Parameter(description = "ID игрока") @PathVariable("id") id: Long,
        @RequestBody playerRequest: PlayerRequest
    ): PlayerResponse {
        val player = playerService.updatePlayer(id, playerRequest)
        return PlayerResponse(player)
    }

    @Operation(
        summary = "Удалить игрока",
        tags = ["Игроки"]
    )
    @DeleteMapping("/{id}/")
    fun deletePlayer(
        @Parameter(description = "ID игрока") @PathVariable("id") id: Long
    ): DetailsResponse {
        playerService.deletePlayer(id)
        return DetailsResponse("Игрок успешно удалён")
    }

    @Operation(
        summary = "Добавить игрока в команду",
        tags = ["Игроки"]
    )
    @PatchMapping("/{id}/add_to_team/")
    fun addToTeam(
        @Parameter(description = "ID игрока") @PathVariable("id") id: Long,
        @RequestBody addToTeamRequest: AddToTeamRequest
    ): DetailsResponse {
        playerService.addPlayerToTeam(id, addToTeamRequest)
        return DetailsResponse("Игрок успешно добавлен в команду")
    }

    @Operation(
        summary = "Убрать игрока из команды",
        tags = ["Игроки"]
    )
    @PatchMapping("/{id}/remove_from_team/")
    fun removeFromTeam(
        @Parameter(description = "ID игрока") @PathVariable("id") id: Long
    ): DetailsResponse {
        playerService.removePlayerFromTeam(id)
        return DetailsResponse("Игрок успешно убран из команды")
    }

}
