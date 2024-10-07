package com.dpds.kokos_football_club.team;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface TeamRepository : JpaRepository<Team, Long>, PagingAndSortingRepository<Team, Long> {
}