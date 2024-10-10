package com.dpds.kokos_football_club.team;

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface TeamRepository : CrudRepository<Team, Long>, PagingAndSortingRepository<Team, Long> {
}