package com.example.saha_plus.data.remote

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.saha_plus.R
import com.example.saha_plus.data.local.database.AppDatabase
import com.example.saha_plus.data.local.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import com.example.saha_plus.data.repository.UserRepository
import com.example.saha_plus.data.view_model.user.UserViewModel
import com.example.saha_plus.data.view_model.user.UserViewModelFactory
import com.example.saha_plus.util.ClearFocusFromEditText
import com.example.saha_plus.util.StatusBarColor
import java.util.*

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etDateNaissance: EditText
    private lateinit var userViewModel: UserViewModel
    private lateinit var rootView: View

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        rootView = findViewById<View>(R.id.signup)
        StatusBarColor.setStatusBarColor(this.window, R.color.blue)
        ClearFocusFromEditText.setupUI(rootView)

        val userDao = AppDatabase.getInstance(this).userDao()
        val userRepository = UserRepository(userDao)
        val factory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        val register = findViewById<Button>(R.id.create_account)
        val etEmail = findViewById<EditText>(R.id.email)
        val etPassword = findViewById<EditText>(R.id.password)
        val etName = findViewById<EditText>(R.id.nom)
        etDateNaissance = findViewById(R.id.date_naissance)
        val login = findViewById<TextView>(R.id.login)

        // Show DatePickerDialog when clicking on the EditText date_naissance
        etDateNaissance.setOnClickListener {
            showDatePicker()
        }

        // Redirect to LoginActivity when clicking on the "login" text
        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close the current CreateAccountActivity
        }

        // Registration process
        register.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val name = etName.text.toString().trim()
            val dateNaissance = etDateNaissance.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || dateNaissance.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = calculateAge(dateNaissance)
            if (age < 18) {
                Toast.makeText(this, "Vous devez avoir au moins 18 ans pour vous inscrire", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // User registration process
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("CreateAccountActivity", "Utilisateur créé avec succès.")
                        val localUser = userViewModel.getCurrentUser()?.let { it1 ->
                            User(
                                userId = it1,
                                username = name,
                                email = email,
                                passwordHash = password,
                                birthday = dateNaissance,
                                createdAt = Date(),
                                updatedAt = Date()
                            )
                        }

                        if (localUser != null) {
                            userViewModel.insertUser(localUser)
                        }

                        Log.i("LocalData", "onCreate:${userViewModel.getCurrentUser()
                            ?.let { it1 -> userViewModel.getUserById(it1) }} ")


                        // Redirection vers la page de connexion après l'inscription
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish() // Ferme CreateAccountActivity

                        // Mise à jour du profil utilisateur avec le nom
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        user?.updateProfile(profileUpdates)

                        // Enregistrement des informations de l'utilisateur dans Firestore
                        val db = FirebaseFirestore.getInstance()
                        val userUpdates = hashMapOf(
                            "date_naissance" to dateNaissance,
                            "age" to age,
                            "email" to email,
                            "name" to name,
                            "password" to password
                        )

                        user?.let {
                            db.collection("users").document(it.uid).set(userUpdates)
                                .addOnSuccessListener {
                                    Log.d("CreateAccountActivity", "Utilisateur enregistré dans Firestore.")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("CreateAccountActivity", "Erreur Firestore : ${e.message}")
                                }
                        }
                    } else {
                        Log.e("CreateAccountActivity", "Échec de l'authentification : ${task.exception?.localizedMessage}")
                        Toast.makeText(this, "Échec de l'inscription : ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    // Fonction pour afficher DatePickerDialog
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etDateNaissance.setText(selectedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    // Fonction pour calculer l'âge en fonction de la date de naissance
    private fun calculateAge(dateNaissance: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val birthDate = dateFormat.parse(dateNaissance) ?: return 0
        val today = Calendar.getInstance()

        val birthCalendar = Calendar.getInstance().apply {
            time = birthDate
        }

        var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

        // Vérifie si l'anniversaire est déjà passé cette année
        if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }
}
