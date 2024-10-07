package com.dpds.kokos_football_club.partner;

import com.dpds.kokos_football_club.exception.DetailsException
import com.dpds.kokos_football_club.personal.RemovePersonalRequest
import com.dpds.kokos_football_club.team.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PartnerService @Autowired constructor(
    private val partnerRepository: PartnerRepository,
    private val teamService: TeamService
){

    fun addPartnerToTeam(teamId: Long, partnerRequest: PartnerRequest) {
        val team = teamService.getTeam(teamId)
        val partner = Partner(
            title = partnerRequest.title,
            img = partnerRequest.img,
            donate = partnerRequest.donate,
            team = team
        )
        team.partners.add(partner)
        partnerRepository.save(partner)
        teamService.saveTeam(team)
    }

    fun removePartnerFromTeam(teamId: Long, removePartnerRequest: RemovePartnerRequest) {
        val team = teamService.getTeam(teamId)
        val partner = partnerRepository.findByIdOrNull(removePartnerRequest.partnerId) ?: throw DetailsException("Спонсор не найден")
        team.partners.remove(partner)
        teamService.saveTeam(team)
        partnerRepository.deleteById(removePartnerRequest.partnerId)
    }
}