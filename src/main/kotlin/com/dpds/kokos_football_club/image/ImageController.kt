package com.dpds.kokos_football_club.image

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/image")
class ImageController @Autowired constructor(
    private val imageService: ImageService,
) {

    @Operation(
        summary = "Получить изображение",
        tags = ["Изображения"]
    )
    @GetMapping("/get/{file_id}/")
    fun getImage(
        authentication: Authentication,
        @PathVariable("file_id") fileId: Long,
        @Parameter(description = "Тип изображения") @RequestParam("type", defaultValue = "FULL") imageType: ImageType,
        request: HttpServletRequest
    ): ResponseEntity<Resource> {
        val image = imageService.loadImage(authentication.name, fileId, imageType)
        val contentType = try {
            request.servletContext.getMimeType(image?.file?.absolutePath)
        } catch (e: Exception) {
            "application/octet-stream"
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${image?.filename}")
            .body(image)
    }

    @Operation(
        summary = "Сохранить изображение",
        tags = ["Изображения"]
    )
    @PostMapping("/save/")
    fun saveImage(
        authentication: Authentication,
        @ModelAttribute body: UploadImageRequest
    ): SavedFile {
        val image = imageService.saveFile(authentication.name, body)
        return SavedFile(image.id)
    }

}