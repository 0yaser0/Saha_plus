package com.example.waterreminder.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.waterreminder.R
import com.example.waterreminder.data.remote.LoginActivity
import com.example.waterreminder.ui.fragment.ObjectivesFragment
import com.example.waterreminder.ui.fragment.TipsFragment
import com.example.waterreminder.ui.fragment.AccountFragment
import com.example.waterreminder.ui.fragment.DashboardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "LoginPrefs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        if (FirebaseAuth.getInstance().currentUser != null) {
            val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
            bottomNavigationView.setOnItemSelectedListener { menuItem ->
                var selectedFragment: Fragment = DashboardFragment()

                when (menuItem.itemId) {
                    R.id.nav_dashboard -> selectedFragment = DashboardFragment()
                    R.id.nav_objectives -> selectedFragment = ObjectivesFragment()
                    R.id.nav_tips -> selectedFragment = TipsFragment()
                    R.id.nav_account -> selectedFragment = AccountFragment()
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()

                true
            }

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DashboardFragment())
                    .commit()
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
