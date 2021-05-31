package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

interface OpenFirstFragmentActionListener {
    fun openFirstFragmentAction(result: Int)
}

class SecondFragment : Fragment() {
    private var listener: OpenFirstFragmentActionListener? = null

    private var backButton: Button? = null
    private var result: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onAttach(context: Context) {
        listener = context as OpenFirstFragmentActionListener
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        val randomValue = generate(min, max)

        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        result?.text = randomValue.toString()

        backButton?.setOnClickListener {
            listener?.openFirstFragmentAction(randomValue)
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return when {
            min < max -> (min..max).random()
            else -> 0
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}