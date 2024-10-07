package com.dpds.kokos_football_club.personal;

import com.dpds.kokos_football_club.exception.DetailsException
import com.dpds.kokos_football_club.team.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PersonalService @Autowired constructor(
    private val personalRepository: PersonalRepository,
    private val teamService: TeamService
){

    fun addPersonalToTeam(teamId: Long, personalRequest: PersonalRequest) {
        val team = teamService.getTeam(teamId)
        val personal = Personal(
            firstName = personalRequest.firstName,
            lastName = personalRequest.lastName,
            age = personalRequest.age,
            type = personalRequest.type,
            team = team
        )
        team.personal.add(personal)
        personalRepository.save(personal)
        teamService.saveTeam(team)
    }

    fun removePersonalFromTeam(teamId: Long, removePersonalRequest: RemovePersonalRequest) {
        val team = teamService.getTeam(teamId)
        val personal = personalRepository.findByIdOrNull(removePersonalRequest.personalId) ?: throw DetailsException("Сотрудник не найден")
        team.personal.remove(personal)
        teamService.saveTeam(team)
        personalRepository.deleteById(removePersonalRequest.personalId)
    }
}