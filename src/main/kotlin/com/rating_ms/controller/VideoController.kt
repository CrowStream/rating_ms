package com.rating_ms.controller
import com.rating_ms.model.Video
import com.rating_ms.model.User
import com.rating_ms.repository.VideoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/rating_ms")
class ArticleController(private val videoRepository: VideoRepository) {

    @GetMapping("/videos")
    fun getAllVideos(): List<Video> =
        videoRepository.findAll()


    @PostMapping("/videos")
    fun createNewVideo(@Valid @RequestBody video: Video): Video =
        videoRepository.save(video)


    @GetMapping("/videos/{id}")
    fun getVideoById(@PathVariable(value = "id") videoId: Long): ResponseEntity<Video> {
        return videoRepository.findById(videoId).map { video ->
            ResponseEntity.ok(video)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/videos/{id}")
    fun updateVideoById(@PathVariable(value = "id") videoId: Long,
                          @Valid @RequestBody newVideo: Video
    ): ResponseEntity<Video> {

        return videoRepository.findById(videoId).map { existingVideo ->
            val updatedVideo: Video = existingVideo
                .copy(likes = newVideo.likes, dislikes = newVideo.dislikes)
            ResponseEntity.ok().body(videoRepository.save(updatedVideo))

        }.orElse(ResponseEntity.notFound().build())

    }


    @DeleteMapping("/videos/{id}")
    fun deleteVideoById(@PathVariable(value = "id") videoId: Long): ResponseEntity<Void> {

        return videoRepository.findById(videoId).map { article  ->
            videoRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}