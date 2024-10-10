package com.dpds.kokos_football_club.match_result;

import org.springframework.data.repository.CrudRepository


interface MatchResultRepository : CrudRepository<MatchResult, Long> {
}