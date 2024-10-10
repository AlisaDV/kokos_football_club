package com.dpds.kokos_football_club.team

import com.dpds.kokos_football_club.image.UploadImageRequest
import com.dpds.kokos_football_club.partner.PartnerRequest
import com.dpds.kokos_football_club.partner.PartnerService
import com.dpds.kokos_football_club.partner.RemovePartnerRequest
import com.dpds.kokos_football_club.personal.PersonalRequest
import com.dpds.kokos_football_club.personal.PersonalService
import com.dpds.kokos_football_club.personal.RemovePersonalRequest
import com.dpds.kokos_football_club.statistic.StatisticRequest
import com.dpds.kokos_football_club.util.DetailsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/team")
class TeamController @Autowired constructor(
    private val teamService: TeamService,
    private val personalService: PersonalService,
    private val partnerService: PartnerService
){
    @Operation(
        summary = "Получить список команд",
        tags = ["Команды"]
    )
    @GetMapping("/")
    fun getTeamList(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: TeamOrdering
    ): PageImpl<TeamResponse> {
        val team = teamService.getTeamList(page, pageSize, ordering, search)
        val teamResponse: MutableList<TeamResponse> = mutableListOf()
        team.forEach {
            teamResponse.add(TeamResponse(it))
        }
        return PageImpl(teamResponse)
    }

    @Operation(
        summary = "Получить команду",
        tags = ["Команды"]
    )
    @GetMapping("/{id}/")
    fun getTeam(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long
    ): TeamProfileResponse {
        val team = teamService.getTeam(id)
        return TeamProfileResponse(team)
    }

    @Operation(
        summary = "Создать команду",
        tags = ["Команды"]
    )
    @PostMapping("/")
    fun createTeam(@RequestBody teamRequest: TeamRequest): TeamResponse {
        val team = teamService.createTeam(teamRequest)
        return TeamResponse(team)
    }

    @Operation(
        summary = "Обновить команду",
        tags = ["Команды"]
    )
    @PutMapping("/{id}/")
    fun updateTeam(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody teamRequest: TeamRequest
    ): TeamResponse {
        val team = teamService.updateTeam(id, teamRequest)
        return TeamResponse(team)
    }

    @Operation(
        summary = "Удалить команду",
        tags = ["Команды"]
    )
    @DeleteMapping("/{id}/")
    fun deleteTeam(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long
    ): DetailsResponse {
        teamService.deleteTeam(id)
        return DetailsResponse("Команда успешно удалена")
    }

    @Operation(
        summary = "Указать статистику команды",
        tags = ["Команды"]
    )
    @PostMapping("/{id}/create_statistic")
    fun sendTeamStatistic(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody statistic: StatisticRequest
    ): DetailsResponse {
        teamService.sendTeamStatistic(id, statistic)
        return DetailsResponse("Статистика успешно указана")
    }

    @Operation(
        summary = "Добавить сотрудника",
        tags = ["Сотрудники"]
    )
    @PatchMapping("/{id}/add_personal")
    fun addPersonalToTeam(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody personalRequest: PersonalRequest
    ): DetailsResponse {
        personalService.addPersonalToTeam(id, personalRequest)
        return DetailsResponse("Сотрудник успешно добавлен")
    }

    @Operation(
        summary = "Убрать сотрудника",
        tags = ["Сотрудники"]
    )
    @PatchMapping("/{id}/remove_personal")
    fun removePersonalFromTeam(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody removePersonalRequest: RemovePersonalRequest
    ): DetailsResponse {
        personalService.removePersonalFromTeam(id, removePersonalRequest)
        return DetailsResponse("Сотрудник успешно убран")
    }

    @Operation(
        summary = "Добавить спонсора",
        tags = ["Спонсоры"]
    )
    @PatchMapping("/{id}/add_partner")
    fun addPartnerToTeam(
        authentication: Authentication,
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody partnerRequest: PartnerRequest
    ): DetailsResponse {
        partnerService.addPartnerToTeam(id, partnerRequest, authentication.name)
        return DetailsResponse("Спонсор успешно добавлен")
    }

    @Operation(
        summary = "Убрать сотрудника",
        tags = ["Сотрудники"]
    )
    @PatchMapping("/{id}/remove_partner")
    fun removePartnerFromTeam(
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody removePartnerRequest: RemovePartnerRequest
    ): DetailsResponse {
        partnerService.removePartnerFromTeam(id, removePartnerRequest)
        return DetailsResponse("Спонсор успешно убран")
    }

    @Operation(
        summary = "Установить логотип",
        tags = ["Изображения"]
    )
    @PatchMapping("/{id}/set-logo/")
    fun setLogo(
        authentication: Authentication,
        @Parameter(description = "ID команды") @PathVariable("id") id: Long,
        @RequestBody imageRequest: UploadImageRequest
    ): DetailsResponse {
        teamService.setLogo(authentication.name, id, imageRequest)
        return DetailsResponse("Логотип успешно установлен")
    }

}