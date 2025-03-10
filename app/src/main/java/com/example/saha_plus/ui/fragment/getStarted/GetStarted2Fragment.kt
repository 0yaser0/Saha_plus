package com.example.saha_plus.ui.fragment.getStarted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.saha_plus.R
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.saha_plus.databinding.GetStarted2Binding
import com.example.saha_plus.ui.activity.GetStarted
import com.example.saha_plus.util.Fragments
import com.example.saha_plus.util.StatusBarColor

class GetStarted2Fragment : Fragments() {
    private var _binding: GetStarted2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetStarted2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        StatusBarColor.setStatusBarColor(requireActivity().window, R.color.blue)

        Glide.with(this)
            .load(R.drawable.ic_water)
            .into(binding.imgGst2)

        binding.next.setOnClickListener {
            (activity as? GetStarted)?.binding?.viewPager?.currentItem = 2
        }

        binding.skip.setOnClickListener {
            (activity as? GetStarted)?.binding?.viewPager?.currentItem = 2
        }

    }
}
