package com.example.waterreminder.ui.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.waterreminder.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etDateNaissance: EditText
    private lateinit var btnSave: ImageView

    private var isModified = false
    private var isLoading = true  // Empêche les faux changements au chargement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        etName = findViewById(R.id.nom)
        etEmail = findViewById(R.id.email)
        etPassword = findViewById(R.id.password)
        etDateNaissance = findViewById(R.id.date_naissance)
        btnSave = findViewById(R.id.save)

        loadUserProfile()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isLoading) { // Évite de déclencher la modification lors du remplissage initial
                    isModified = true
                    btnSave.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etName.addTextChangedListener(textWatcher)
        etEmail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)
        etDateNaissance.addTextChangedListener(textWatcher)

        btnSave.setOnClickListener {
            if (isModified) saveUserProfile()
        }
    }

    private fun loadUserProfile() {
        val user = auth.currentUser

        if (user != null) {
            // Affichage d'un indicateur de chargement si nécessaire
            isLoading = true
            btnSave.visibility = View.GONE // Cache le bouton de sauvegarde pendant le chargement

            // Utilisation d'une coroutine pour charger les données
            GlobalScope.launch {
                try {
                    val document = db.collection("users").document(user.uid).get().await()
                    withContext(Dispatchers.Main) {
                        if (document.exists()) {
                            val name = document.getString("name") ?: ""
                            val email = document.getString("email") ?: ""
                            val dateNaissance = document.getString("date_naissance") ?: ""
                            val password = document.getString("password") ?: ""

                            // Affichage des données dans les champs EditText
                            etName.setText(name)
                            etEmail.setText(email)
                            etPassword.setText(password)
                            etDateNaissance.setText(dateNaissance)

                            // Une fois les données chargées, activer la détection de modification
                            isLoading = false
                        } else {
                            Toast.makeText(this@ProfileActivity, "Aucune donnée utilisateur trouvée.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, "Erreur lors du chargement : ${e.message}", Toast.LENGTH_SHORT).show()
                        isLoading = false
                    }
                }
            }
        } else {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
        }
    }


    private fun saveUserProfile() {
        val user = auth.currentUser

        if (user != null) {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val dateNaissance = etDateNaissance.text.toString().trim()

            val userData = mapOf(
                "name" to name,
                "email" to email,
                "date_naissance" to dateNaissance,
                "password" to password
            )

            GlobalScope.launch {
                try {
                    // Mettre à jour dans Firestore
                    db.collection("users").document(user.uid).set(userData).await()

                    // Mettre à jour l'email et le mot de passe via FirebaseAuth si nécessaire
                    if (email.isNotEmpty() && email != user.email) {
                        user.updateEmail(email).await()
                    }

                    if (password.isNotEmpty()) {
                        user.updatePassword(password).await()
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, "Profil mis à jour", Toast.LENGTH_SHORT).show()
                    }

                    isModified = false
                    withContext(Dispatchers.Main) {
                        btnSave.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, "Erreur lors de la mise à jour : ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
