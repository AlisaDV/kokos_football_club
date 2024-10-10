package com.dpds.kokos_football_club.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "file")
data class MediaFileStorageProperties(
    var uploadDir: String
)
