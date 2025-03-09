package com.example.waterreminder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterreminder.ui.activity.ProfileActivity
import com.example.waterreminder.ui.activity.SettingsActivity
import com.example.waterreminder.data.remote.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class TryActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try)

        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()

        // Google Sign-In Configuration
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize the TextView and set the user info if signed in
        val textView = findViewById<TextView>(R.id.name)
        val user = mAuth.currentUser

        if (user != null) {
            val userName = user.displayName
            textView.text = "Welcome, $userName"
        } else {
            // Handle the case where the user is not signed in, e.g., show a default message
            textView.text = "Please sign in"
        }

        // Set up the sign-out button
        val signOutButton = findViewById<Button>(R.id.logout_button)
        signOutButton.setOnClickListener {
            signOutAndStartSignInActivity()
        }
        // Déclarez la variable pour le bouton "profile"
        val profileButton = findViewById<Button>(R.id.btn)
        val settings_btn= findViewById<Button>(R.id.settings_btn)

        // Ajoutez un OnClickListener pour le bouton "profile"
        profileButton.setOnClickListener {
            // Créez une intention pour démarrer ProfileActivity
            val intent = Intent(this@TryActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        settings_btn.setOnClickListener {
            // Créez une intention pour démarrer ProfileActivity
            val intent = Intent(this@TryActivity, SettingsActivity::class.java)
            startActivity(intent)}

    }

    private fun signOutAndStartSignInActivity() {
        // Sign out from Firebase
        mAuth.signOut()

        // Sign out from Google
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            // After sign-out, start the SignInActivity
            val intent = Intent(this@TryActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
