package com.example.saha_plus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.saha_plus.data.remote.LoginActivity
import com.example.saha_plus.ui.fragment.ObjectivesFragment
import com.example.saha_plus.ui.fragment.TipsFragment
import com.example.saha_plus.ui.fragment.AccountFragment
import com.example.saha_plus.ui.fragment.DashboardFragment
import com.example.saha_plus.util.Activity
import com.example.saha_plus.R
import com.example.saha_plus.util.ClearFocusFromEditText
import com.example.saha_plus.util.StatusBarColor
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : Activity() {
    private lateinit var rootView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        rootView = findViewById<View>(R.id.main)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        StatusBarColor.setStatusBarColor(this.window, R.color.blue)
        ClearFocusFromEditText.setupUI(rootView)

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
