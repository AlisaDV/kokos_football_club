package com.dpds.kokos_football_club.match_result;

import org.springframework.data.jpa.repository.JpaRepository

interface MatchResultRepository : JpaRepository<MatchResult, Long> {
}