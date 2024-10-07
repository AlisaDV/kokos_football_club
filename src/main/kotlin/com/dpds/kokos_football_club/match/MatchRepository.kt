package com.dpds.kokos_football_club.match;

import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository : JpaRepository<Match, Long> {
}