package com.rating_ms.model
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import javax.persistence.*

@Entity
data class  liked_videos(
    @EmbeddedId
    var id: LikedId? = null,

    @JsonIgnoreProperties("liked")
    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id",nullable = true)
    @MapsId("user_id")
    var user : User,

    @JsonIgnoreProperties("liked")
    @JsonProperty("video")
    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "video_id", nullable = true)
    @MapsId("video_id")
    var video : Video,

    @Column
    var like : Int
)