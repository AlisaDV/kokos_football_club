package com.dpds.kokos_football_club.ex_player;

import org.springframework.data.repository.CrudRepository


interface ExPlayerRepository : CrudRepository<ExPlayer, Long> {
}