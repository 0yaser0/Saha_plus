package com.example.waterreminder.ui.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.waterreminder.R
import com.example.waterreminder.data.local.database.AppDatabase
import com.example.waterreminder.data.local.model.WaterConsumption
import com.example.waterreminder.data.repository.UserRepository
import com.example.waterreminder.data.repository.WaterConsumptionRepository
import com.example.waterreminder.data.view_model.user.UserViewModel
import com.example.waterreminder.data.view_model.user.UserViewModelFactory
import com.example.waterreminder.data.view_model.water_consumption.WaterConsumptionViewModel
import com.example.waterreminder.data.view_model.water_consumption.WaterConsumptionViewModelFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var viewModel: WaterConsumptionViewModel
    private lateinit var todayText: TextView
    private lateinit var chartToday: BarChart
    private lateinit var chartWeek: BarChart
    private  var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todayText = view.findViewById(R.id.tv_today_consumption)
        chartToday = view.findViewById(R.id.barChartToday)
        chartWeek = view.findViewById(R.id.barChartWeek)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add_consumption)

        // Setup ViewModels
        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val userRepository = UserRepository(userDao)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]

        val db = AppDatabase.getInstance(requireContext())
        val consumptionRepo = WaterConsumptionRepository(db.waterConsumptionDao())
        viewModel = ViewModelProvider(this, WaterConsumptionViewModelFactory(consumptionRepo))[WaterConsumptionViewModel::class.java]

        userId = userViewModel.getCurrentUser()

        userId?.let {
            loadCharts(it)
        }

        fab.setOnClickListener {
            userId?.let {
                showAddDialog(it)
            }
        }
    }

    private fun loadCharts(userId: String) {
        viewModel.getTodayTotal(userId) { total ->
            todayText.text = "Vous avez bu : ${String.format("%.2f", total)}L aujourd'hui"
            updateTodayChart(chartToday, total)
        }

        viewModel.getWeeklyStats(userId) { weekMap ->
            updateWeekChart(chartWeek, weekMap)
        }
    }

    private fun updateTodayChart(chart: BarChart, volume: Float) {
        val entries = listOf(BarEntry(0f, volume))
        val dataSet = BarDataSet(entries, "Aujourd'hui (L)").apply {
            color = Color.parseColor("#4CAF50")
        }
        chart.data = BarData(dataSet)
        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(listOf("Aujourd'hui"))
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.invalidate()
    }

    private fun updateWeekChart(chart: BarChart, weekMap: Map<String, Float>) {
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        var index = 0
        weekMap.forEach { (day, volume) ->
            entries.add(BarEntry(index.toFloat(), volume))
            labels.add(day)
            index++
        }

        val dataSet = BarDataSet(entries, "Semaine (L)").apply {
            color = Color.parseColor("#3F51B5")
        }
        chart.data = BarData(dataSet)
        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.invalidate()
    }

    private fun showAddDialog(userId: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ajouter consommation")

        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        input.hint = "Quantité en L (ex: 0.5)"
        builder.setView(input)

        builder.setPositiveButton("Ajouter") { _, _ ->
            val value = input.text.toString().toFloatOrNull()
            if (value != null && value > 0) {
                viewModel.insertConsumption(
                    WaterConsumption(
                        userId = userId,
                        timestamp = Date(),
                        volume = value
                    )
                )

                loadCharts(userId)
            } else {
                Log.e("Error", "Quantité invalide")
            }
        }

        builder.setNegativeButton("Annuler") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}
