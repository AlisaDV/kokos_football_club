package com.dpds.kokos_football_club.statistic;

import org.springframework.data.jpa.repository.JpaRepository

interface StatisticRepository : JpaRepository<Statistic, Long> {
}