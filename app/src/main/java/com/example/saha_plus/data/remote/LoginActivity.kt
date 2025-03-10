package com.example.saha_plus.data.remote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.saha_plus.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.core.content.edit
import com.example.saha_plus.ui.activity.HomeActivity
import com.example.saha_plus.util.ClearFocusFromEditText
import com.example.saha_plus.util.StatusBarColor

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 9001
        const val PREFS_NAME = "LoginPrefs"
        const val KEY_REMEMBER_ME = "rememberMe"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var rbRememberMe: CheckBox
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        rbRememberMe = findViewById(R.id.rb_remember_me)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvSignup = findViewById<TextView>(R.id.tv_signup)
        val signInButton = findViewById<Button>(R.id.signInButton)

       rootView = findViewById<View>(R.id.login_activity)
        StatusBarColor.setStatusBarColor(this.window, R.color.blue)
        ClearFocusFromEditText.setupUI(rootView)

        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val rememberMe = preferences.getBoolean(KEY_REMEMBER_ME, false)
        if (rememberMe && auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener { loginUser() }
        tvSignup.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CreateAccountActivity::class.java
                )
            )
        }
        signInButton.setOnClickListener { signInWithGoogle() }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (rbRememberMe.isChecked) {
                        preferences.putBoolean(KEY_REMEMBER_ME, true)
                    } else {
                        preferences.remove(KEY_REMEMBER_ME)
                    }
                    preferences.apply()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Échec de connexion Google: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    preferences.edit() { putBoolean(KEY_REMEMBER_ME, true) }
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Échec de l'authentification", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
