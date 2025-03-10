package com.example.saha_plus.ui.fragment.getStarted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.saha_plus.databinding.GetStarted1Binding
import com.example.saha_plus.util.Fragments
import com.example.saha_plus.util.StatusBarColor
import com.example.saha_plus.R
import com.example.saha_plus.ui.activity.GetStarted

class GetStarted1Fragment : Fragments() {
    private var _binding: GetStarted1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GetStarted1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        StatusBarColor.setStatusBarColor(requireActivity().window, R.color.blue)

        jumpingAnimation(binding.jumpingImage)

        binding.next.setOnClickListener {
            (activity as? GetStarted)?.binding?.viewPager?.currentItem = 1
        }

        binding.skip.setOnClickListener {
            (activity as? GetStarted)?.binding?.viewPager?.currentItem = 2
        }
    }

    private fun jumpingAnimation(imageView: ImageView) {
        //startJumpingAnimation(imageView, -50f, 1000, 20)
    }
}
