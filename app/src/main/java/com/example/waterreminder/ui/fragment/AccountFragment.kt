package com.example.waterreminder.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.waterreminder.R
import com.example.waterreminder.data.remote.LoginActivity
import com.example.waterreminder.ui.activity.ProfileActivity
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var settingsList: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        backButton = view.findViewById(R.id.btnBack)
        settingsList = view.findViewById(R.id.settingsList)

        // Configure back button
        backButton.setOnClickListener {
            requireActivity().onBackPressed() // Navigate back
        }

        // List of settings options
        val settingsOptions = arrayOf(
            "Profil",
            "Consommation d'eau",
            "Notifications",
            "Langue",
            "Déconnexion"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, settingsOptions)
        settingsList.adapter = adapter

        settingsList.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> navigateToProfile()
                1 -> navigateToWaterConsumption()
                2 -> navigateToNotifications()
                3 -> navigateToLanguage()
                4 -> logout()
            }
        }
    }

    private fun navigateToProfile() {
        // Navigate to profile screen
        val intent = Intent(requireContext(), ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToWaterConsumption() {
        // Navigate to water consumption screen
        // Replace with the correct activity or fragment logic
    }

    private fun navigateToNotifications() {
        // Navigate to notifications screen
        // Replace with the correct activity or fragment logic
    }

    private fun navigateToLanguage() {
        // Navigate to language selection screen
        // Replace with the correct activity or fragment logic
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        val preferences = requireActivity().getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE)
        preferences.edit() { remove(LoginActivity.KEY_REMEMBER_ME) }

        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}
