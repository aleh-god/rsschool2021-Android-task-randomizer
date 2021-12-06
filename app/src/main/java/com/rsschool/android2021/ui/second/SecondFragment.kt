package com.rsschool.android2021.ui.second

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rsschool.android2021.ActionPerformedListener
import com.rsschool.android2021.R
import com.rsschool.android2021.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private var listener: ActionPerformedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionPerformedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val result = arguments?.getInt(VALUE_KEY) ?: 0
        binding.result.text = result.toString()

        binding.back.setOnClickListener {
            listener?.onActionPerformedOne(result)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(result: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(VALUE_KEY, result)
            fragment.arguments = args
            return fragment
        }
        private const val VALUE_KEY = "VALUE"
    }
}
