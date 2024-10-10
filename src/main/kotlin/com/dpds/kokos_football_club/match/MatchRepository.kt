package com.dpds.kokos_football_club.match;

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface MatchRepository : CrudRepository<Match, Long>, PagingAndSortingRepository<Match, Long> {
}