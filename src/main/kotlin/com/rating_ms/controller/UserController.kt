package com.rating_ms.controller
import com.rating_ms.model.User
import com.rating_ms.model.Video
import com.rating_ms.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/rating_ms")
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/users")
    fun getAllVideos(): List<User> = userRepository.findAll()


    @PostMapping("/users")
    fun createNewVideo(@Valid @RequestBody user: User): User =
        userRepository.save(user)


    @GetMapping("/users/{id}")
    fun getVideoById(@PathVariable(value = "id") videoId: Long): ResponseEntity<User> {
        return userRepository.findById(videoId).map { video ->
            ResponseEntity.ok(video)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/users/{id}")
    fun updateUserById(@PathVariable(value = "id") userId: Long,
                        @Valid @RequestBody newId: Long): ResponseEntity<User> {

        return userRepository.findById(userId).map { existingUser ->
            existingUser.id = newId
            val updatedUser = existingUser
            ResponseEntity.ok().body(userRepository.save(updatedUser))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/users/{id}")
    fun deleteVideoById(@PathVariable(value = "id") Id: Long): ResponseEntity<Void> {

        return userRepository.findById(Id).map { user  ->
            userRepository.delete(user)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}