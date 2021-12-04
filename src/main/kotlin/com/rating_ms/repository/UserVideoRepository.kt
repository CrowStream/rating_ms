package com.rating_ms.repository
import com.rating_ms.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserVideoRepository : JpaRepository<liked_videos, LikedId>