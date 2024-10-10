package com.dpds.kokos_football_club.player;

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface PlayerRepository : CrudRepository<Player, Long>, PagingAndSortingRepository<Player, Long> {
}