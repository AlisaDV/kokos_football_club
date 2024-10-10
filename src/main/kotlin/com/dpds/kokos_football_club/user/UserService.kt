package com.dpds.kokos_football_club.user

import com.dpds.kokos_football_club.exception.DetailsException
import com.dpds.kokos_football_club.exception.NotFoundException
import com.dpds.kokos_football_club.image.ImageService
import com.dpds.kokos_football_club.image.UploadImageRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val imageService: ImageService
) {

    fun existByLogin(login: String): Boolean {
        return userRepository.existsByLogin(login)
    }

    fun getByLogin(login: String): User {
        return userRepository.findByLogin(login) ?: throw NotFoundException("Пользователь не найден")
    }

    fun getUserList(
        page: Int,
        pageSize: Int,
        ordering: UserOrdering,
        search: String
    ): Page<User> {
        val users = userRepository.findAll().filter {it.login.lowercase().contains(search.lowercase())
                || it.firstName.lowercase().contains(search.lowercase())
                || it.lastName.lowercase().contains(search.lowercase())
        }

        val sort = when(ordering) {
            UserOrdering.ID_ASC -> Sort.by(Sort.Direction.ASC, "id")
            UserOrdering.ID_DESC -> Sort.by(Sort.Direction.DESC, "id")
            UserOrdering.LOGIN_ASC -> Sort.by(Sort.Direction.ASC, "login")
            UserOrdering.LOGIN_DESC -> Sort.by(Sort.Direction.DESC, "login")
            UserOrdering.AGE_ASC -> Sort.by(Sort.Direction.ASC, "age")
            UserOrdering.AGE_DESC -> Sort.by(Sort.Direction.DESC, "age")
        }
        val pageRequest = PageRequest.of(page, pageSize, sort)
        return PageImpl(users.drop(pageSize * page).take(pageSize), pageRequest, users.size.toLong())
    }

    fun getUser(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw NotFoundException("Пользователь не найден")
    }

    fun createUser(userRequest: UserRequest): User {
        if(userRepository.existsByLogin(userRequest.login)) {
           throw DetailsException("Пользователь уже существует")
        }
        return userRepository.save(
            User(
                login = userRequest.login,
                password = passwordEncoder.encode(userRequest.password),
                firstName = userRequest.firstName,
                lastName = userRequest.lastName,
                email = userRequest.email,
                age = userRequest.age,
                role = UserRole.USER
            )
        )
    }

    fun updateUser(userRequest: UpdateUserRequest, id: Long): User {
        val user = getUser(id)
        user.firstName = userRequest.firstName
        user.lastName = userRequest.lastName
        user.email = userRequest.email
        user.age = userRequest.age
        return userRepository.save(user)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun changePassword(id: Long, passwordRequest: ChangePasswordRequest) {
        if(!userRepository.existsById(id)) {
            throw NotFoundException("Пользователь не найден")
        }
        val user = getUser(id)
        user.password = passwordEncoder.encode(passwordRequest.password)
        userRepository.save(user)
    }

    fun blockUser(id: Long) {
        val user = getUser(id)
        user.isBlocked = true
        userRepository.save(user)
    }

    fun unblockUser(id: Long) {
        val user = getUser(id)
        user.isBlocked = false
        userRepository.save(user)
    }

    fun giveAdminRights(id: Long) {
        val user = getUser(id)
        user.role = UserRole.ADMIN
        userRepository.save(user)
    }

    fun takeAdminRights(id: Long) {
        val user = getUser(id)
        user.role = UserRole.USER
        userRepository.save(user)
    }

    fun setAvatar( id: Long, avatarRequest: UploadImageRequest) {
        val user = getUser(id)
        user.avatar = imageService.saveFile(user.login, avatarRequest)
        userRepository.save(user)
    }
}