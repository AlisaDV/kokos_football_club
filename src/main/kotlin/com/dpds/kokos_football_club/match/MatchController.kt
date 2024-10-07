package com.dpds.kokos_football_club.match

import com.dpds.kokos_football_club.match_result.MatchResultRequest
import com.dpds.kokos_football_club.team.TeamRequest
import com.dpds.kokos_football_club.team.TeamResponse
import com.dpds.kokos_football_club.util.DetailsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/match/")
class MatchController @Autowired constructor(
    private val matchService: MatchService
){
    @Operation(
        summary = "Получить список матчей",
        tags = ["Матчи"]
    )
    @GetMapping("/")
    fun getMatchList(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: MatchOrdering
    ): PageImpl<MatchResponse> {
        val match = matchService.getMatchList(page, pageSize, ordering, search)
        val matchResponse: MutableList<MatchResponse> = mutableListOf()
        match.forEach {
            matchResponse.add(MatchResponse(it))
        }
        return PageImpl(matchResponse)
    }

    @Operation(
        summary = "Получить матч",
        tags = ["Матчи"]
    )
    @GetMapping("/{id}/")
    fun getMatch(
        @Parameter(description = "ID матча") @PathVariable("id") id: Long
    ): MatchResponse {
        val match = matchService.getMatch(id)
        return MatchResponse(match)
    }

    @Operation(
        summary = "Создать матч",
        tags = ["Матчи"]
    )
    @PostMapping("/")
    fun createMatch(@RequestBody matchRequest: MatchRequest): MatchResponse {
        val match = matchService.createMatch(matchRequest)
        return MatchResponse(match)
    }

    @Operation(
        summary = "Обновить матч",
        tags = ["Матчи"]
    )
    @PutMapping("/{id}/")
    fun updateMatch(
        @Parameter(description = "ID матча") @PathVariable("id") id: Long,
        @RequestBody matchRequest: MatchRequest
    ): MatchResponse {
        val match = matchService.updateMatch(id, matchRequest)
        return MatchResponse(match)
    }

    @Operation(
        summary = "Удалить матч",
        tags = ["Матчи"]
    )
    @DeleteMapping("/{id}/")
    fun deleteMatch(
        @Parameter(description = "ID матча") @PathVariable("id") id: Long
    ): DetailsResponse {
        matchService.deleteMatch(id)
        return DetailsResponse("Матч успешно удален")
    }

    @Operation(
        summary = "Установить результат матча",
        tags = ["Матчи"]
    )
    @PatchMapping("/{id}/send_match_result/")
    fun sendMatchResult(
        @Parameter(description = "ID матча") @PathVariable("id") id: Long,
        @RequestBody matchResult: MatchResultRequest
    ): DetailsResponse {
        matchService.sendMatchResult(id, matchResult)
        return DetailsResponse("Результат успешно установлен")
    }
}