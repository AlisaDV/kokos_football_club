package com.dpds.kokos_football_club.user

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    fun existsByLogin(login: String): Boolean
    fun findByLogin(login: String): User?
}