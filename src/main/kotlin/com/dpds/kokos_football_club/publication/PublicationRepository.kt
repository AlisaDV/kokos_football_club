package com.dpds.kokos_football_club.publication;

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface PublicationRepository : CrudRepository<Publication, Long>, PagingAndSortingRepository<Publication, Long> {
    fun findAllByType(type: PublicationType, pageable: Pageable): Page<Publication>
}