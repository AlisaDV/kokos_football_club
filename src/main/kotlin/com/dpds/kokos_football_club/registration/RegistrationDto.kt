package com.dpds.kokos_football_club.registration

import com.dpds.kokos_football_club.user.User
import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("login")
    val login: String,
    @JsonProperty("full_name")
    var fullName: String,
    @JsonProperty("email")
    var email: String,
    @JsonProperty("age")
    var age: Int,
) {
    constructor(user: User): this (
        id = user.id,
        login = user.login,
        fullName = user.firstName + " " + user.lastName,
        email = user.email,
        age = user.age
    )
}
