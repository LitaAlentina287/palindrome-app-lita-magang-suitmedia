package com.example.litapalindromechecker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPalindrome: EditText
    private lateinit var btnCheck: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi view berdasarkan ID di activity_main.xml
        etName = findViewById(R.id.et_name)
        etPalindrome = findViewById(R.id.et_palindrome)
        btnCheck = findViewById(R.id.btn_check)
        btnNext = findViewById(R.id.btn_next)

        // Tombol CHECK untuk validasi palindrome
        btnCheck.setOnClickListener {
            val input = etPalindrome.text.toString()
            val cleaned = input.filter { it.isLetterOrDigit() }.lowercase()
            val isPalindrome = cleaned == cleaned.reversed()
            val message = if (isPalindrome) "isPalindrome" else "not palindrome"

            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }

        // Tombol NEXT untuk lanjut ke halaman berikutnya
        btnNext.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isEmpty()) {
                etName.error = "Please enter your name"
            } else {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }
}
