package com.example.waterreminder.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterreminder.data.repository.HydrationTipRepository
import com.example.waterreminder.data.local.database.AppDatabase
import com.example.waterreminder.data.view_model.hydration_tip.HydrationTipViewModel
import com.example.waterreminder.data.view_model.hydration_tip.HydrationTipViewModelFactory
import com.example.waterreminder.databinding.FragmentTipsBinding
import com.example.waterreminder.ui.adapter.TipsAdapter

class TipsActivity : AppCompatActivity() {

    private lateinit var binding: FragmentTipsBinding
    private lateinit var viewModel: HydrationTipViewModel
    private lateinit var tipsAdapter: TipsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getInstance(this)
        val repository = HydrationTipRepository(database.hydrationTipDao())

        val viewModelFactory = HydrationTipViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HydrationTipViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.tips.observe(this) { tips ->
            tipsAdapter = TipsAdapter(tips)
            binding.recyclerView.adapter = tipsAdapter
        }

        viewModel.loadTips()
    }
}