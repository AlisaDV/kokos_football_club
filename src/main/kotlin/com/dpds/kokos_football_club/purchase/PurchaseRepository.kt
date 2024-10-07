package com.dpds.kokos_football_club.purchase;

import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<Purchase, Long> {
}