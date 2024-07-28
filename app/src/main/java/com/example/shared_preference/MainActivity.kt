package com.example.shared_preference

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var usuarioEditText: EditText;
    private lateinit var passwordEditText: EditText;

    private lateinit var loginButton: Button;

    private lateinit var sharedPreferences: SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        usuarioEditText = findViewById(R.id.usuarioEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        loginButton = findViewById(R.id.loginButton);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        revisarLogin();

        loginButton.setOnClickListener {
            val usuario = usuarioEditText.text.toString();
            val password = passwordEditText.text.toString();

            if (usuario == "admin" && password == "password") {
                guardarLogin(true);

                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                val intent = Intent(this, ActivityInit::class.java);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Credenciales incorrectas.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun guardarLogin(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit();

        editor.putBoolean("isLoggedIn", isLoggedIn);

        editor.apply();
    }

    private fun revisarLogin() {
        val verificarLogin = sharedPreferences.getBoolean("isLoggedIn", false);

        if (verificarLogin) {
            Toast.makeText(this, "Sesion iniciada.", Toast.LENGTH_SHORT).show();

            val intent = Intent(this, ActivityInit::class.java);
            startActivity(intent);
        }
    }
}