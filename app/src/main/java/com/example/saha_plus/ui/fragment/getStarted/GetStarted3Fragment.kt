package com.example.saha_plus.ui.fragment.getStarted

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.saha_plus.databinding.GetStarted3Binding
import com.example.saha_plus.util.Fragments
import com.example.saha_plus.R
import com.example.saha_plus.data.remote.LoginActivity
import com.example.saha_plus.util.StatusBarColor

@Suppress("DEPRECATION")
class GetStarted3Fragment : Fragments() {
    private var _binding: GetStarted3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = GetStarted3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        StatusBarColor.setStatusBarColor(requireActivity().window, R.color.blue)

        Glide.with(this).load(R.drawable.ic_water).into(binding.imgGst2)

        binding.btnGetstarted.setOnClickListener {

            startActivity(Intent(requireActivity(), LoginActivity::class.java))

            requireActivity().overridePendingTransition(
                R.animator.slide_in_right, R.animator.slide_out_left
            )
        }
    }
}
