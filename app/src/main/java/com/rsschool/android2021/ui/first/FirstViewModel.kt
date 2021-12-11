package com.rsschool.android2021.ui.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rsschool.android2021.MAX_ERROR_TEXT
import com.rsschool.android2021.MIN_ERROR_TEXT
import com.rsschool.android2021.domain.model.RandomNumberModel
import com.rsschool.android2021.domain.model.ResultModel
import com.rsschool.android2021.domain.usecase.GetRandomNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val getRandomNumberUseCase: GetRandomNumberUseCase
): ViewModel() {

    val previousResult = MutableLiveData("Previous result: 0")
    val minValueString = MutableLiveData("0")
    val maxValueString = MutableLiveData("1")

    val result: LiveData<ResultModel<Int>> = MediatorLiveData<ResultModel<Int>>().apply {
        addSource(minValueString) {
            value = checkMinVsMax()
        }
        addSource(maxValueString) {
            value = checkMinVsMax()
        }
    }

    val generateButton: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(result) {
            value = when (it) {
                is ResultModel.Failure<Int> -> false
                is ResultModel.Success -> true
            }
        }
    }

    private fun checkMin(): ResultModel<Int> {
        val result = minValueString.value?.toIntOrNull()
        return if (result == null || result < 0) ResultModel.Failure(MIN_ERROR_TEXT)
        else ResultModel.Success(result)
    }

    private fun checkMax(): ResultModel<Int> {
        val result = maxValueString.value?.toIntOrNull()
        return if (result == null || result < 1) ResultModel.Failure(MAX_ERROR_TEXT)
        else ResultModel.Success(result)
    }

    private fun checkMinVsMax(): ResultModel<Int> {
        val min = checkMin()
        val max = checkMax()
        return getRandomNumberUseCase.execute(RandomNumberModel(min, max))
    }
}
