package com.release.fast.mvp.model

data class RecommendPageBean (
    val obj: Obj,
    var code: Int,
    var msg: String
)


data class Obj(
    val items: Items,
    val url: String
)

data class Items(
    val rows: List<Row>
)

data class Row(
    val noticeCreateTime: String,
    val noticeId: String,
    val noticeTitle: String,
    val sectoring: String
)



