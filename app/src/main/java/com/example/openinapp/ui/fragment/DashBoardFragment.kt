package com.example.openinapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinapp.databinding.FragmentDashBoardBinding
import com.example.openinapp.ui.ViewModel.DashBoardViewModel
import com.example.openinapp.ui.adapters.LinksAdapter
import com.example.openinapp.ui.adapters.TopItemsAdapter
import com.example.openinapp.utils.SliderTooltip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    private val animationDuration = 1000L
    private var _binding: FragmentDashBoardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: DashBoardViewModel by viewModels()
        var lineChart = _binding!!.lineChart


        lineChart.gradientFillColors = intArrayOf(
            Color.parseColor("#81FFFFFF"),
            Color.TRANSPARENT
        )

        lineChart.animation.duration = animationDuration
        lineChart.tooltip =
            SliderTooltip().also {
                it.color = Color.WHITE
            }

        viewModel.greeting?.observe(viewLifecycleOwner, Observer {
            _binding!!.greeting.text = it.toString()
        })

        viewModel.linksList?.observe(viewLifecycleOwner, Observer {
            _binding!!.linkRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            _binding!!.linkRecyclerView.adapter = LinksAdapter(it)

        })

        viewModel.topItemsList?.observe(viewLifecycleOwner, Observer {
            val HorizontalLayout = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            _binding!!.analyticalRecyclerView.layoutManager = HorizontalLayout
            _binding!!.analyticalRecyclerView.adapter = TopItemsAdapter(it, requireContext())

        })

        viewModel.lineSet?.observe(viewLifecycleOwner, Observer {
            lineChart.animate(it)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}