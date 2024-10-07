package com.dpds.kokos_football_club.ex_player;

import org.springframework.data.jpa.repository.JpaRepository

interface ExPlayerRepository : JpaRepository<ExPlayer, Long> {
}