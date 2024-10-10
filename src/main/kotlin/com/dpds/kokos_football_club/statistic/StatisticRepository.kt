package com.dpds.kokos_football_club.statistic;

import org.springframework.data.repository.CrudRepository


interface StatisticRepository : CrudRepository<Statistic, Long> {
}