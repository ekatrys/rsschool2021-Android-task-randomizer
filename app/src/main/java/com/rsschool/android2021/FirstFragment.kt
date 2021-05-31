package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

interface OpenSecondFragmentActionListener {
    fun openSecondFragmentAction(min: Int, max: Int)
}

class FirstFragment : Fragment() {
    private var listener: OpenSecondFragmentActionListener? = null

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        listener = context as OpenSecondFragmentActionListener
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        val minView = view.findViewById<EditText>(R.id.min_value)
        val maxView = view.findViewById<EditText>(R.id.max_value)

        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val min = minView.text.toString().toIntOrNull()
            val max = maxView.text.toString().toIntOrNull()
            when {
                min == null -> minView.error = "Enter min value"
                max == null -> maxView.error = "Enter max value"
                min >= max -> maxView.error = "Max value must be bigger than min"
                else -> listener?.openSecondFragmentAction(min, max)
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