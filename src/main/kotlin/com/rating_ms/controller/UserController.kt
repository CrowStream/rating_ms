package com.rating_ms.controller
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rating_ms.model.LikedId
import com.rating_ms.model.POJOS.liking
import com.rating_ms.model.POJOS.updateLike
import com.rating_ms.model.User
import com.rating_ms.model.Video
import com.rating_ms.model.liked_videos
import com.rating_ms.repository.UserRepository
import com.rating_ms.repository.UserVideoRepository
import com.rating_ms.repository.VideoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.xml.bind.DatatypeConverter.parseString


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

    @GetMapping("/getLikedVideos")
    fun getLikedVideos(): liking {
        var response = liking()
        for (i in userVideoRepository.findAll()){
            if(i.like == 0) {
                response.likes.add(i.id)
            }else{
                response.dislikes.add(i.id)
            }
        }

        return response;
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
    fun likingVideo(@Valid @RequestBody liked: updateLike): liked_videos {
        var userliked = userRepository.getById(liked.user_id).liked
        var rated = false;
        var change = false;
        for (i in userliked){
            if(liked.video_id==i.video.video_id){
                if(liked.like!=i.like){
                    change = true
                }
                rated = true;
                break;
            }
        }
        var updating = liked_videos(
            LikedId(liked.user_id,liked.video_id),
            userRepository.getById(liked.user_id),
            videoRepository.getById(liked.video_id),liked.like
        )
        if(!rated || (rated&&change)){
            if (liked.like == 1) {
                if(!change){
                    var oldVideo = videoRepository.findById(liked.video_id).get()
                    val updatedVideo: Video = oldVideo.copy(likes = oldVideo.likes + 1)
                    videoRepository.save(updatedVideo)
                    updating.video = updatedVideo
                }else{
                    var oldVideo = videoRepository.findById(liked.video_id).get()
                    val updatedVideo: Video = oldVideo.copy(likes = oldVideo.likes + 1, dislikes = oldVideo.dislikes - 1)
                    videoRepository.save(updatedVideo)
                    updating.video = updatedVideo
                }

            } else {
                if(!change){
                    var oldVideo = videoRepository.findById(liked.video_id).get()
                    val updatedVideo: Video = oldVideo.copy(dislikes = oldVideo.likes + 1)
                    videoRepository.save(updatedVideo)
                    updating.video = updatedVideo
                }else{
                    var oldVideo = videoRepository.findById(liked.video_id).get()
                    val updatedVideo: Video = oldVideo.copy(dislikes = oldVideo.dislikes + 1, likes = oldVideo.likes - 1)
                    videoRepository.save(updatedVideo)
                    updating.video = updatedVideo
                }
            }
        }
        return userVideoRepository.save(updating)
    }

    @DeleteMapping("/users/{id}")
    fun deleteUserById(@PathVariable(value = "id") Id: Long): ResponseEntity<Void> {

        return userRepository.findById(Id).map { user  ->
            userRepository.delete(user)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}