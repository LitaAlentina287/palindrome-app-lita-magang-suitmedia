package com.example.litapalindromechecker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var tvSelectedUser: TextView
    private lateinit var btnChooseUser: Button
    private lateinit var btnBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvUserName = findViewById(R.id.tvUserName)
        tvSelectedUser = findViewById(R.id.tvSelectedUser)
        btnChooseUser = findViewById(R.id.btnChooseUser)
        btnBack = findViewById(R.id.btnBack)

        // Ambil nama dari intent sebelumnya
        val name = intent.getStringExtra("name")
        tvUserName.text = name ?: ""

        // Tombol kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Pilih user dari ThirdActivity
        btnChooseUser.setOnClickListener {
            val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val selectedUser = data?.getStringExtra("selected_user_name")
            tvSelectedUser.text = selectedUser ?: "Selected User Name"
        }
    }
}
