package com.example.biletoss

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var editTextEmail: TextInputEditText
    lateinit var editTextPassword: TextInputEditText
    lateinit var signIn: Button
    lateinit var signUp: TextView
    lateinit var rememberMeCheckbox: CheckBox
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        signIn = findViewById(R.id.sign_in)
        signUp = findViewById(R.id.sign_up)

        rememberMeCheckbox = findViewById(R.id.remember_me_checkbox)
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")
        val rememberMeChecked = sharedPreferences.getBoolean("rememberMe", false)

        signUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (rememberMeChecked) {
            editTextEmail.setText(savedEmail)
            editTextPassword.setText(savedPassword)
            rememberMeCheckbox.isChecked = true
        }

        signIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Emailinizi giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Parolanızı giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rememberMe = rememberMeCheckbox.isChecked
            if (rememberMe) {
                val editor = sharedPreferences.edit()
                editor.putString("email", email)
                editor.putString("password", password)
                editor.putBoolean("rememberMe", true)
                editor.apply()
            } else {
                val editor = sharedPreferences.edit()
                editor.remove("email")
                editor.remove("password")
                editor.remove("rememberMe")
                editor.apply()
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Giriş başarılı", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, HomePage::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Giriş başarısız", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}