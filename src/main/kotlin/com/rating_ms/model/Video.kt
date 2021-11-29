package com.rating_ms.model
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Video (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val likes: Int = 0,

    @Column
    val dislikes: Int = 0,

    @ManyToMany(mappedBy = "videos")
    val liked: Set<User>
)