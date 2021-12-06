package com.rsschool.android2021.domain.model

data class RandomNumberModel(
    val min: ResultModel<Int>,
    val max: ResultModel<Int>
)