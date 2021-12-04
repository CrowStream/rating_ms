package com.rating_ms.controller
import com.rating_ms.model.User
import com.rating_ms.model.Video
import com.rating_ms.model.liked_videos
import com.rating_ms.repository.UserRepository
import com.rating_ms.repository.UserVideoRepository
import com.rating_ms.repository.VideoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/rating_ms")
class UserController(private val userRepository: UserRepository,
                     private val videoRepository: VideoRepository,
                     private val userVideoRepository: UserVideoRepository) {

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
            existingUser.user_id = newId
            val updatedUser = existingUser
            ResponseEntity.ok().body(userRepository.save(updatedUser))
        }.orElse(ResponseEntity.notFound().build())

    }

    @PostMapping("/users/likevideo")
    fun likingVideo(@Valid @RequestBody liked: liked_videos): liked_videos {
        var userliked = userRepository.getById(liked.user.user_id).liked
        var rated = false;
        for (i in userliked){
            if(liked.video.video_id==i.video.video_id){
                rated = true;
                break;
            }
        }
        liked.video = videoRepository.getById(liked.video.video_id)
        if(!rated){
            if (liked.like == 1) {
                var oldVideo = videoRepository.findById(liked.video.video_id).get()
                val updatedVideo: Video = oldVideo.copy(likes = oldVideo.likes + 1)
                videoRepository.save(updatedVideo)
                liked.video = updatedVideo
            } else {
                var oldVideo = videoRepository.findById(liked.video.video_id).get()
                val updatedVideo: Video = oldVideo.copy(dislikes = oldVideo.dislikes + 1)
                videoRepository.save(updatedVideo)
                liked.video = updatedVideo
            }
        }
        return userVideoRepository.save(liked)
    }

    @DeleteMapping("/users/{id}")
    fun deleteVideoById(@PathVariable(value = "id") Id: Long): ResponseEntity<Void> {

        return userRepository.findById(Id).map { user  ->
            userRepository.delete(user)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}