package com.dpds.kokos_football_club.player;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface PlayerRepository : JpaRepository<Player, Long>, PagingAndSortingRepository<Player, Long> {
}