package com.example.litapalindromechecker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.litapalindromechecker.adapter.UserAdapter
import com.example.litapalindromechecker.model.User
import com.example.litapalindromechecker.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {

    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userAdapter: UserAdapter
    private lateinit var tvEmpty: TextView

    private val userList = mutableListOf<User>()
    private var currentPage = 1
    private var totalPages = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Force Light Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        // View binding
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        tvEmpty = findViewById(R.id.tvEmpty)

        // Back button
        val btnBack = findViewById<TextView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // Setup adapter
        userAdapter = UserAdapter(userList) { user ->
            val resultIntent = Intent()
            resultIntent.putExtra("selected_user_name", "${user.first_name} ${user.last_name}")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        recyclerViewUsers.adapter = userAdapter

        // Swipe refresh
        swipeRefreshLayout.setOnRefreshListener {
            refreshUsers()
        }

        // Infinite scroll
        recyclerViewUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && lastVisibleItem + 3 >= totalItemCount && currentPage < totalPages) {
                    loadUsers(currentPage + 1)
                }
            }
        })

        // Initial load
        loadUsers(currentPage)
    }

    private fun refreshUsers() {
        currentPage = 1
        userList.clear()
        userAdapter.notifyDataSetChanged()
        loadUsers(currentPage)
    }

    private fun loadUsers(page: Int) {
        isLoading = true
        swipeRefreshLayout.isRefreshing = true
        tvEmpty.visibility = View.GONE

        ApiClient.apiService.getUsers(page).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                isLoading = false
                swipeRefreshLayout.isRefreshing = false

                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (userResponse != null && userResponse.data.isNotEmpty()) {
                        currentPage = userResponse.page
                        totalPages = userResponse.total_pages
                        userList.addAll(userResponse.data)
                        userAdapter.notifyDataSetChanged()
                        tvEmpty.visibility = View.GONE
                        Log.d("ThirdActivity", "Loaded users: ${userResponse.data.size}")
                    } else {
                        tvEmpty.visibility = View.VISIBLE
                        Log.w("ThirdActivity", "No users found")
                    }
                } else {
                    Toast.makeText(this@ThirdActivity, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
                    tvEmpty.visibility = View.VISIBLE
                    Log.e("ThirdActivity", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading = false
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@ThirdActivity, "Gagal terhubung: ${t.message}", Toast.LENGTH_LONG).show()
                tvEmpty.visibility = View.VISIBLE
                Log.e("ThirdActivity", "API failure", t)
            }
        })
    }
}
