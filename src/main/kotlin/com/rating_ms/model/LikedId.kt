package com.rating_ms.model

import java.io.Serializable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class LikedId(
    @Column(name = "user_id")
    var user_id: Long = 0,

    @Column(name = "video_id")
    var video_id: Long = 0

): Serializable