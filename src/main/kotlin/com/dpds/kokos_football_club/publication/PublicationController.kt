package com.dpds.kokos_football_club.publication

import com.dpds.kokos_football_club.util.DetailsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/publication/")
class PublicationController @Autowired constructor(
    private val publicationService: PublicationService
){

    @Operation(
        summary = "Получить список публикаций",
        tags = ["Публикации"]
    )
    @GetMapping("/")
    fun getPublicationList(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: PublicationOrdering
    ): PageImpl<PublicationResponse> {
        val publication = publicationService.getPublicationList(page, pageSize, ordering, search)
        val publicationResponse: MutableList<PublicationResponse> = mutableListOf()
        publication.forEach {
            publicationResponse.add(PublicationResponse(it))
        }
        return PageImpl(publicationResponse)
    }

    @Operation(
        summary = "Получить список публикаций",
        tags = ["Публикации"]
    )
    @GetMapping("/{type}/")
    fun getPublicationListByType(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: PublicationOrdering,
        @Parameter(description = "Тип публикации") @PathVariable("type") type: PublicationType
    ): PageImpl<PublicationResponse> {
        val publication = publicationService.getPublicationListByType(page, pageSize, ordering, search, type)
        val publicationResponse: MutableList<PublicationResponse> = mutableListOf()
        publication.forEach {
            publicationResponse.add(PublicationResponse(it))
        }
        return PageImpl(publicationResponse)
    }

    @Operation(
        summary = "Получить публикацию",
        tags = ["Публикации"]
    )
    @GetMapping("/{id}/")
    fun getPublication(
        @Parameter(description = "ID публикации") @PathVariable("id") id: Long
    ): PublicationResponse {
        return PublicationResponse(publicationService.getPublication(id))
    }

    @Operation(
        summary = "Обновить публикациб",
        tags = ["Публикации"]
    )
    @PutMapping("/{id}/")
    fun updatePublication(
        @Parameter(description = "ID публикации") @PathVariable("id") id: Long,
        @RequestBody publicationRequest: UpdatePublicationRequest
    ): PublicationResponse {
        return PublicationResponse(publicationService.updatePublication(id, publicationRequest))
    }

    @Operation(
        summary = "Удалить пользователя",
        tags = ["Публикации"]
    )
    @DeleteMapping("/{id}/")
    fun deletePublication(
        @Parameter(description = "ID публикации") @PathVariable("id") id: Long
    ): DetailsResponse {
        publicationService.deletePublication(id)
        return DetailsResponse("Пользователь успешно удалён")
    }


}