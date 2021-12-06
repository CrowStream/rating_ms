package com.rating_ms.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
data class User (
    @Id
    var user_id: String = "",
    @OneToMany(mappedBy = "user", cascade = [CascadeType.PERSIST,
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.REMOVE])
    @JsonIgnoreProperties("user")
    val liked: MutableList<liked_videos> = mutableListOf()
)
