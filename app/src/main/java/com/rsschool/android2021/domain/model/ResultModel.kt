package com.rsschool.android2021.domain.model

sealed class ResultModel<T> {
    data class Success<T>(val data: T) : ResultModel<T>()
    data class Failure<T>(val message: Int) : ResultModel<T>()
}