package com.example.waterreminder.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterreminder.R
import com.example.waterreminder.data.remote.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import androidx.core.content.edit
import com.example.waterreminder.ui.activity.HomeActivity.Companion.PREFS_NAME

class SettingsActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var settingsList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialisation de la vue
        backButton = findViewById(R.id.btnBack)
        settingsList = findViewById(R.id.settingsList)

        // Titre de l'activité
        supportActionBar?.hide()

        // Configuration du bouton retour
        backButton.setOnClickListener {
            onBackPressed() // Retour à l'activité précédente
        }

        // Liste des options de settings
        val settingsOptions = arrayOf(
            "Profil",
            "Consommation d'eau",
            "Notifications",
            "Langue",
            "Déconnexion"
        )

        // Adapter pour la ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, settingsOptions)
        settingsList.adapter = adapter

        // Gestion du clic sur une option
        settingsList.setOnItemClickListener { parent, view, position, id ->
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
        // Naviguer vers l'activité de profil
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToWaterConsumption() {
        // Naviguer vers l'activité de consommation d'eau
        // Remplacer avec l'activité appropriée
    }

    private fun navigateToNotifications() {
        // Naviguer vers l'activité de notifications
        // Remplacer avec l'activité appropriée
    }

    private fun navigateToLanguage() {
        // Naviguer vers l'activité de sélection de langue
        // Remplacer avec l'activité appropriée
    }

    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
