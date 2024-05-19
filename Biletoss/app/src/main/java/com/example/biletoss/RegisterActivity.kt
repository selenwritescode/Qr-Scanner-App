package com.example.biletoss

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var editTextEmail: TextInputEditText
    lateinit var editTextPassword: TextInputEditText
    lateinit var signUp: Button
    lateinit var signIn: TextView
    lateinit var usernameDisplay: TextInputEditText
    lateinit var confirmPassword: TextInputEditText
    lateinit var phoneNumber: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        signIn = findViewById(R.id.sign_in)
        signUp = findViewById(R.id.sign_up)
        usernameDisplay = findViewById(R.id.username)
        confirmPassword = findViewById(R.id.confirm_password)
        phoneNumber = findViewById(R.id.phone_number)


        signIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        signUp.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val confirm_password = confirmPassword.text.toString().trim()
            val username = usernameDisplay.text.toString().trim()
            val phone_number = phoneNumber.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Adınızı ve Soyadınızı giriniz",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Emailinizi giriniz", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (phone_number.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Telefon numaranızı giriniz",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Parolanızı giriniz", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (confirm_password.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Bu alan boş bırakılamaz", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (password != confirm_password) {
                Toast.makeText(this@RegisterActivity, "Parolalar eşleşmiyor", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Parola en az 6 karakter uzunluğunda olmalı",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Kayıt Başarılı",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Kayıt Başarısız",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    }
}