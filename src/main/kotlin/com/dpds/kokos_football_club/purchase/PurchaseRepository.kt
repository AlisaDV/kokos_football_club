package com.dpds.kokos_football_club.purchase;

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository


interface PurchaseRepository : CrudRepository<Purchase, Long>, PagingAndSortingRepository<Purchase, Long> {
}