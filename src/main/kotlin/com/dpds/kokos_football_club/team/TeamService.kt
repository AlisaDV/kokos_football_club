package com.dpds.kokos_football_club.team;

import com.dpds.kokos_football_club.exception.NotFoundException
import com.dpds.kokos_football_club.image.ImageService
import com.dpds.kokos_football_club.image.UploadImageRequest
import com.dpds.kokos_football_club.statistic.Statistic
import com.dpds.kokos_football_club.statistic.StatisticRepository
import com.dpds.kokos_football_club.statistic.StatisticRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TeamService @Autowired constructor(
    private val teamRepository: TeamRepository,
    private val statisticRepository: StatisticRepository,
    private val imageService: ImageService
){

    fun getTeamList(
        page: Int,
        pageSize: Int,
        ordering: TeamOrdering,
        search: String
    ): PageImpl<Team> {
        val sort = when(ordering) {
            TeamOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            TeamOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            TeamOrdering.TITLE_ASC -> Sort.by(Sort.Direction.ASC, "title")
            TeamOrdering.TITLE_DESC -> Sort.by(Sort.Direction.DESC, "title")
        }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        val teams = teamRepository.findAll(pageRequest).filter {
            it.title.lowercase().contains(search.lowercase())
        }.toMutableList()
        return PageImpl(teams.drop(pageSize * page).take(pageSize), pageRequest, teams.size.toLong())
    }

    fun getTeam(id: Long): Team {
        return teamRepository.findByIdOrNull(id) ?: throw NotFoundException("Пользователь не найден")
    }

    fun saveTeam(team: Team): Team {
        return teamRepository.save(team)
    }

    fun createTeam(teamRequest: TeamRequest): Team {
        return saveTeam(
            Team(
                title = teamRequest.title,
                description = teamRequest.description,
                statistic = null
            )
        )
    }

    fun updateTeam(id: Long, teamRequest: TeamRequest): Team {
        val team = getTeam(id)
        team.title = teamRequest.title
        team.description = teamRequest.description
        return saveTeam(team)
    }

    fun deleteTeam(id: Long) {
        teamRepository.deleteById(id)
    }

    fun sendTeamStatistic(id: Long, statisticRequest: StatisticRequest) {
        val team = getTeam(id)
        val statistic = Statistic(
            date = statisticRequest.date,
            numberOfGoals = statisticRequest.numberOfGoals,
            numberOfHitsToFrame = statisticRequest.numberOfHitsToFrame,
            numberOfPoints = statisticRequest.numberOfPoints,
            numberOfPasses = statisticRequest.numberOfPasses,
            levelBallControl = statisticRequest.levelBallControl
        )
        team.statistic = statistic
        statisticRepository.save(statistic)
        teamRepository.save(team)
    }

    fun setLogo(login: String, teamId: Long, avatarRequest: UploadImageRequest) {
        val team = getTeam(teamId)
        team.logo = imageService.saveFile(login, avatarRequest)
        teamRepository.save(team)
    }

}