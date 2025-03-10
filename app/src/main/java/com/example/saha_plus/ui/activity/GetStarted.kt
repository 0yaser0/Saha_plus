package com.example.saha_plus.ui.activity

import android.os.Bundle
import com.example.saha_plus.R
import com.example.saha_plus.databinding.GetStartedBinding
import com.example.saha_plus.ui.adapter.GetStartedAdapter
import com.example.saha_plus.util.Activity
import com.example.saha_plus.util.ClearFocusFromEditText
import com.example.saha_plus.util.StatusBarColor

class GetStarted : Activity() {

    lateinit var binding: GetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StatusBarColor.setStatusBarColor(this.window, R.color.blue)
        ClearFocusFromEditText.setupUI(binding.root)

        val adapter = GetStartedAdapter(this)
        binding.viewPager.adapter = adapter
        binding.indicator.setViewPager(binding.viewPager)
    }
}