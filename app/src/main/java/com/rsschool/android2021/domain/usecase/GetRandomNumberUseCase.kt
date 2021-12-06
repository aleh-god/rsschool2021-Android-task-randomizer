package com.rsschool.android2021.domain.usecase

import com.rsschool.android2021.domain.model.RandomNumberModel
import com.rsschool.android2021.domain.model.ResultModel
import javax.inject.Inject
import kotlin.random.Random

class GetRandomNumberUseCase @Inject constructor() {
    fun execute(params: RandomNumberModel): ResultModel<Int> {
        var result: ResultModel<Int> = ResultModel.Failure("null")
        val from = params.min
        val until = params.max
        var min = 0
        var max = 1
        when (from) {
            is ResultModel.Failure<Int> -> {
                result = from
            }
            is ResultModel.Success -> {
                min = from.data
                when (until) {
                    is ResultModel.Failure<Int> -> {
                        result = until
                    }
                    is ResultModel.Success -> {
                        max = until.data
                        result = if (min < max) {
                            ResultModel.Success(
                                Random.nextInt(from = min, until = max)
                            )
                        } else {
                            ResultModel.Failure("Min value > or = Max value")
                        }
                    }
                }
            }
        }
        return result
    }
}