package com.dpds.kokos_football_club.publication;

import com.dpds.kokos_football_club.exception.NotFoundException
import com.dpds.kokos_football_club.user.User
import com.dpds.kokos_football_club.user.UserOrdering
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PublicationService @Autowired constructor(
    private val publicationRepository: PublicationRepository
) {

    fun getPublication(id: Long): Publication {
        return publicationRepository.findByIdOrNull(id) ?: throw NotFoundException("Публикация не найдена")
    }

    fun getPublicationList(
        page: Int,
        pageSize: Int,
        ordering: PublicationOrdering,
        search: String
    ): PageImpl<Publication> {
        val publication = publicationRepository.findAll().filter { it.title.lowercase().contains(search.lowercase()) }

        val sort = when (ordering) {
            PublicationOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            PublicationOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            PublicationOrdering.TITLE_ASC -> Sort.by(Sort.Direction.ASC, "title")
            PublicationOrdering.TITLE_DESC -> Sort.by(Sort.Direction.DESC, "title")
            PublicationOrdering.DATE_PUBLICATION_ASC -> Sort.by(Sort.Direction.ASC, "datePublication")
            PublicationOrdering.DATE_PUBLICATION_DESC -> Sort.by(Sort.Direction.DESC, "datePublication")
        }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        return PageImpl(publication.drop(pageSize * page).take(pageSize), pageRequest, publication.size.toLong())
    }

    fun getPublicationListByType(
        page: Int,
        pageSize: Int,
        ordering: PublicationOrdering,
        search: String,
        type: PublicationType
    ): PageImpl<Publication> {
        val sort = when (ordering) {
            PublicationOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            PublicationOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            PublicationOrdering.TITLE_ASC -> Sort.by(Sort.Direction.ASC, "title")
            PublicationOrdering.TITLE_DESC -> Sort.by(Sort.Direction.DESC, "title")
            PublicationOrdering.DATE_PUBLICATION_ASC -> Sort.by(Sort.Direction.ASC, "datePublication")
            PublicationOrdering.DATE_PUBLICATION_DESC -> Sort.by(Sort.Direction.DESC, "datePublication")
        }

        val pageRequest = PageRequest.of(page, pageSize, sort)
        val publication = publicationRepository.findAllByType(type, pageRequest).filter { it.title.lowercase().contains(search.lowercase()) }.toList()
        return PageImpl(publication.drop(pageSize * page).take(pageSize), pageRequest, publication.size.toLong())
    }

    fun createPublication(publicationRequest: PublicationRequest): Publication {
        return publicationRepository.save(
            Publication(
                title = publicationRequest.title,
                description = publicationRequest.description,
                datePublication = publicationRequest.datePublication,
                img = publicationRequest.img,
                type = publicationRequest.type
            )
        )
    }

    fun updatePublication(id: Long, publicationRequest: UpdatePublicationRequest): Publication {
        val publication = getPublication(id)
        publication.title = publicationRequest.title
        publication.description = publicationRequest.description
        publication.img = publicationRequest.img
        return publicationRepository.save(publication)
    }

    fun deletePublication(id: Long) {
        publicationRepository.deleteById(id)
    }

}