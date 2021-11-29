package com.rating_ms.model
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column
    val name: String = "",
    @ManyToMany
    @JoinTable(
        name = "liked_videos",
        joinColumns = [JoinColumn(name = "User_id")],
        inverseJoinColumns = [JoinColumn(name = "Video_id")]
    )
    val videos: Set<Video>?
)