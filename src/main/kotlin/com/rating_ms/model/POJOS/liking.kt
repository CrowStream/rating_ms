package com.rating_ms.model.POJOS

import com.rating_ms.model.LikedId


class liking(
    var dislikes: MutableList<LikedId> = mutableListOf(),
    var likes: MutableList<LikedId> = mutableListOf()
)