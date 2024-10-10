package com.dpds.kokos_football_club.image;

import com.dpds.kokos_football_club.exception.DetailsException
import com.dpds.kokos_football_club.exception.ForbiddenException
import com.dpds.kokos_football_club.exception.NotFoundException
import com.dpds.kokos_football_club.properties.MediaFileStorageProperties
import com.dpds.kokos_football_club.user.UserRepository
import com.dpds.kokos_football_club.user.UserService
import org.imgscalr.Scalr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.deleteIfExists
import kotlin.math.min

@Service
class ImageService @Autowired constructor(
    private val mediaFileStorageProperties: MediaFileStorageProperties,
    private val imageRepository: ImageRepository,
    private val userRepository: UserRepository,
) {
    private var fileStorageLocation: Path = Paths.get(mediaFileStorageProperties.uploadDir).toAbsolutePath().normalize()

    init {
        fileStorageLocation = Paths.get(mediaFileStorageProperties.uploadDir).toAbsolutePath().normalize()
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getImage(id: Long): Image {
        return imageRepository.findByIdOrNull(id) ?: throw NotFoundException("Файл не найден")
    }


    fun saveFile(username: String, request: UploadImageRequest): Image {
        val user =  userRepository.findByLogin(username) ?: throw DetailsException("Пользователь не найден")
        val fileType = request.image.originalFilename!!.split('.').last()
        val sourceFileName = request.image.originalFilename!!
        val targetPath = fileStorageLocation.resolve("${mediaFileStorageProperties.uploadDir}/images/${user.id}")
        val timestamp = Calendar.getInstance().timeInMillis
        val fullFileName: String = StringUtils.cleanPath("full_$timestamp.$fileType")
        val previewFileName: String = StringUtils.cleanPath("preview_$timestamp.$fileType")
        Files.createDirectories(targetPath)
        try {
            val imageInputStream = ImageIO.createImageInputStream(request.image.inputStream)
            val readers = ImageIO.getImageReaders(imageInputStream)
            val reader = readers.next()
            reader.setInput(imageInputStream, true, true)
            var previewImage = reader.read(0)
            if (previewImage.height > 200 || previewImage.width > 200) {
                var heightCoefficient = 200f / previewImage.height
                var widthCoefficient = 200f / previewImage.width
                var coefficient = min(heightCoefficient, widthCoefficient)
                previewImage = Scalr.resize(previewImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, (previewImage.width * coefficient).toInt(), (previewImage.height * coefficient).toInt())
            }
            Files.copy(request.image.inputStream, targetPath.resolve(fullFileName), StandardCopyOption.REPLACE_EXISTING)
            val previewByteArray = ByteArrayOutputStream()
            ImageIO.write(previewImage, fileType, previewByteArray)
            val fullFilePath = targetPath.resolve(fullFileName)
            val previewFilePath = targetPath.resolve(previewFileName)
            Files.copy(request.image.inputStream, fullFilePath, StandardCopyOption.REPLACE_EXISTING)
            Files.copy(ByteArrayInputStream(previewByteArray.toByteArray()), previewFilePath, StandardCopyOption.REPLACE_EXISTING)
            val image = imageRepository.save(
                Image(
                    fullFilePath = "${mediaFileStorageProperties.uploadDir}/images/$user.id/$fullFileName",
                    previewFilePath = "${mediaFileStorageProperties.uploadDir}/images/$user.id/$previewFileName",
                    createdBy = user
                )
            )
            return image
        } catch (e: IOException) {
            throw e
        }
    }

    fun loadImage(username: String, fileId: Long, imageType: ImageType): Resource? {
        return try {
            val file = getImage(fileId)
            val filePath = if (imageType == ImageType.FULL) {
                file.fullFilePath
            } else {
                file.previewFilePath
            }
            val resource: Resource = UrlResource(fileStorageLocation.resolve(filePath).toUri())
            if (resource.exists()) {
                resource
            } else {
                throw DetailsException("Файл не найден")
            }
        } catch (ex: MalformedURLException) {
            throw DetailsException("Файл не найден")
        }
    }

    fun loadImageForArticle(imageId: Long): Resource? {
        return try {
            val file = getImage(imageId)
            val filePath = file.fullFilePath
            val resource: Resource = UrlResource(fileStorageLocation.resolve(filePath).toUri())
            if (resource.exists()) {
                resource
            } else {
                throw DetailsException("Файл не найден")
            }
        } catch (ex: MalformedURLException) {
            throw DetailsException("Файл не найден")
        }
    }

    fun deleteFile(catalogs: List<String>, fileName: String) {
        try {
            val filePath = catalogs.fold(fileStorageLocation) { path, catalog -> path.resolve(catalog) }.resolve(fileName).normalize()
            filePath.deleteIfExists()
        } catch (ex: MalformedURLException) {
            throw DetailsException("Файл не найден")
        }
    }

    fun moveFile(fromCatalogs: List<String>, toCatalogs: List<String>, fileName: String): String {
        try {
            val sourceFile = fromCatalogs.fold(fileStorageLocation) { path, catalog -> path.resolve(catalog) }.resolve(fileName).normalize()
            val targetDirectory = toCatalogs.fold(fileStorageLocation) { path, catalog -> path.resolve(catalog) }
            val targetFile = targetDirectory.resolve(fileName).normalize()
            Files.createDirectories(targetDirectory)
            Files.copy(sourceFile, targetFile)
            deleteFile(fromCatalogs, fileName)
            return "${targetDirectory.joinToString("/")}/$fileName"
        } catch (ex: MalformedURLException) {
            throw DetailsException("Файл не найден")
        }
    }

    fun getUserImage(username: String, imageId: Long?): Image? {
        val image = imageId?.let { imageRepository.findByIdOrNull(imageId) }
        if (image != null && !image.isCreatedByUser(username)) {
            throw ForbiddenException("Доступ запрещён")
        }
        return image
    }

    fun getImageForUse(imageId: Long?, username: String): Image? {
        return imageId?.let {
            val image = imageRepository.findByIdOrNull(imageId)
            if (image != null && image.createdBy?.login != username) {
                throw ForbiddenException("Доступ запрещён")
            }
            image
        }
    }
}