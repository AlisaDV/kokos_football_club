package com.dpds.kokos_football_club.user

import com.dpds.kokos_football_club.util.DetailsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user/")
class UserController @Autowired constructor(
    private val userService: UserService
) {
    @Operation(
        summary = "Получить список пользователей",
        tags = ["Пользователи"]
    )
    @GetMapping("/")
    fun getUserList(
        @Parameter(description = "Номер страницы") @RequestParam("page", defaultValue = "0") page: Int,
        @Parameter(description = "Размер страницы") @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        @Parameter(description = "Поиск по названию") @RequestParam("search", defaultValue = "") search: String,
        @Parameter(description = "Сортировка") @RequestParam("ordering", defaultValue = "ID_ASC") ordering: UserOrdering
    ): PageImpl<UserResponse> {
        val user = userService.getUserList(page, pageSize, ordering, search)
        val userResponse: MutableList<UserResponse> = mutableListOf()
        user.forEach {
            userResponse.add(UserResponse(it))
        }
        return PageImpl(userResponse)
    }

    @Operation(
        summary = "Получить пользователя",
        tags = ["Пользователи"]
    )
    @GetMapping("/{id}/")
    fun getUser(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long
    ): UserResponse {
        return UserResponse(userService.getUser(id))
    }

    @Operation(
        summary = "Обновить пользователя",
        tags = ["Пользователи"]
    )
    @PutMapping("/{id}/")
    fun updateUser(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long,
        @RequestBody userRequest: UpdateUserRequest
    ): UserResponse {
        return UserResponse(userService.updateUser(userRequest, id))
    }

    @Operation(
        summary = "Удалить пользователя",
        tags = ["Пользователи"]
    )
    @DeleteMapping("/{id}/")
    fun deleteUser(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long
    ): DetailsResponse {
        userService.deleteUser(id)
        return DetailsResponse("Пользователь успешно удалён")
    }

    @Operation(
        summary = "Изменить пароль",
        tags = ["Пользователи"]
    )
    @PatchMapping("/{id}/change-password")
    fun changePassword(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long,
        @RequestBody passwordRequest: ChangePasswordRequest
    ): DetailsResponse {
        userService.changePassword(id, passwordRequest)
        return DetailsResponse("Пароль успешно изменён")
    }

    @Operation(
        summary = "Заблокировать пользователя",
        tags = ["Пользователи"]
    )
    @PatchMapping("/{id}/block")
    fun blockUser(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long,
    ): DetailsResponse {
        userService.blockUser(id)
        return DetailsResponse("Пользователь успешно заблокирован")
    }

    @Operation(
        summary = "Разблокировать пользователя",
        tags = ["Пользователи"]
    )
    @PatchMapping("/{id}/unblock")
    fun unblockUser(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long,
    ): DetailsResponse {
        userService.unblockUser(id)
        return DetailsResponse("Пользователь успешно разблокирован")
    }

    @Operation(
        summary = "Дать права администратора",
        tags = ["Пользователи"]
    )
    @PatchMapping("/{id}/give-admin")
    fun giveAdminRights(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long,
    ): DetailsResponse {
        userService.giveAdminRights(id)
        return DetailsResponse("Права успешно выданы")
    }

    @Operation(
        summary = "Забрать права администратора",
        tags = ["Пользователи"]
    )
    @PatchMapping("/{id}/take-admin")
    fun takeAdminRights(
        @Parameter(description = "ID пользователя") @PathVariable("id") id: Long,
    ): DetailsResponse {
        userService.takeAdminRights(id)
        return DetailsResponse("Права успешно выданы")
    }
}