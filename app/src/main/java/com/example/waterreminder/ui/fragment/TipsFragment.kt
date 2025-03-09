package com.example.waterreminder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterreminder.data.local.model.HydrationTip
import com.example.waterreminder.databinding.FragmentTipsBinding
import com.example.waterreminder.ui.adapter.TipsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class TipsFragment : Fragment() {

    private var _binding: FragmentTipsBinding? = null
    private val binding get() = _binding!!
    private lateinit var tipsAdapter: TipsAdapter
    private lateinit var tipsList: List<HydrationTip>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        tipsList = loadTipsFromJson()

        tipsAdapter = TipsAdapter(tipsList)
        binding.recyclerView.adapter = tipsAdapter
    }

    private fun loadTipsFromJson(): List<HydrationTip> {
        val jsonString: String
        try {
            jsonString = requireContext().assets.open("hydration_tips.json")
                .bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }
        val gson = Gson()
        val listTipType = object : TypeToken<List<HydrationTip>>() {}.type
        return gson.fromJson(jsonString, listTipType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
