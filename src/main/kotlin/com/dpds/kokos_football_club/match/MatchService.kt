package com.dpds.kokos_football_club.match;

import com.dpds.kokos_football_club.exception.NotFoundException
import com.dpds.kokos_football_club.match_result.MatchResult
import com.dpds.kokos_football_club.match_result.MatchResultRepository
import com.dpds.kokos_football_club.match_result.MatchResultRequest
import com.dpds.kokos_football_club.team.Team
import com.dpds.kokos_football_club.team.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MatchService @Autowired constructor(
    private val matchRepository: MatchRepository,
    private val teamService: TeamService,
    private val matchResultRepository: MatchResultRepository
) {
    fun getMatchList(
        page: Int,
        pageSize: Int,
        ordering: MatchOrdering,
        search: String
    ): PageImpl<Match> {
        val matches = matchRepository.findAll().filter {
            it.title.lowercase().contains(search.lowercase())
        }

        val sort = when (ordering) {
            MatchOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            MatchOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            MatchOrdering.DATE_ASC -> Sort.by(Sort.Direction.ASC, "date")
            MatchOrdering.DATE_DESC -> Sort.by(Sort.Direction.DESC, "date")
        }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        return PageImpl(matches.drop(pageSize * page).take(pageSize), pageRequest, matches.size.toLong())
    }

    fun getMatch(id: Long): Match {
        return matchRepository.findByIdOrNull(id) ?: throw NotFoundException("Пользователь не найден")
    }

    fun createMatch(matchRequest: MatchRequest): Match {
        val teams: MutableList<Team> = mutableListOf()
        matchRequest.teams.forEach {
            val team = teamService.getTeam(it.teamId)
            teams.add(team)
        }
        return matchRepository.save(
            Match(
                date = matchRequest.date,
                title = matchRequest.title,
                teams = teams,
                matchResult = null
            )
        )
    }

    fun updateMatch(id: Long, matchRequest: MatchRequest): Match {
        val match = getMatch(id)
        val teams: MutableList<Team> = mutableListOf()
        matchRequest.teams.forEach {
            val team = teamService.getTeam(it.teamId)
            teams.add(team)
        }
        match.title = matchRequest.title
        match.date = matchRequest.date
        match.teams = teams
        return matchRepository.save(match)
    }

    fun deleteMatch(id: Long) {
        matchRepository.deleteById(id)
    }

    fun sendMatchResult(id: Long, matchResultRequest: MatchResultRequest) {
        val team = teamService.getTeam(matchResultRequest.teamId)
        val match = getMatch(id)
        val matchResult = MatchResult(
            team = team,
            match = match
        )
        match.matchResult = matchResult
        matchResultRepository.save(matchResult)
        matchRepository.save(match)
    }
}