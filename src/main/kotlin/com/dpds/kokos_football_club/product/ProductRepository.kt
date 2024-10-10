package com.dpds.kokos_football_club.product;

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository


interface ProductRepository : CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
}