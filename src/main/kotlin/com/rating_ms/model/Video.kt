package com.rating_ms.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
data class Video (
    @Id
    val video_id: Long = 0,

    @Column
    val likes: Int = 0,

    @Column
    val dislikes: Int = 0,

    @OneToMany(mappedBy = "video", cascade = [CascadeType.PERSIST,
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.REMOVE], fetch=FetchType.LAZY)
    @JsonIgnoreProperties("video")
    val liked: MutableList<liked_videos> = mutableListOf()
)