package com.rsschool.android2021.ui.first

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rsschool.android2021.*
import com.rsschool.android2021.databinding.FragmentFirstBinding
import com.rsschool.android2021.domain.model.ResultModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private var listener: ActionPerformedListener? = null
    private val viewModel: FirstViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionPerformedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.previousResult.value = arguments?.getInt(PREVIOUS_RESULT_KEY).toString()
        setupUI()
    }

    private fun setupUI() {
        viewModel.result.observe(this) {
            when (it) {
                is ResultModel.Failure<Int> -> {
                    when (it.message) {
                         MIN_ERROR_TEXT -> {
                            binding.minValue.error = getString(R.string.minErrorText)
                        }
                         MAX_ERROR_TEXT -> {
                            binding.maxValue.error = getString(R.string.maxErrorText)
                        }
                         DOUBLE_ERROR_TEXT -> {
                            binding.maxValue.error = getString(R.string.doubleErrorText)
                            binding.minValue.error = getString(R.string.doubleErrorText)
                        }
                    }
                }
                is ResultModel.Success -> {
                    binding.maxValue.error = null
                    binding.minValue.error = null
                    val result = it.data
                    binding.generate.setOnClickListener {
                        listener?.onActionPerformedTwo(result)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }
        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}
