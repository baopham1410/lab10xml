package com.example.lab10_2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private val sinhVienList = mutableListOf<Students>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 🔹 Gán Adapter với danh sách rỗng trước
        studentAdapter = StudentAdapter(sinhVienList)
        recyclerView.adapter = studentAdapter

        // 🔹 Gọi API để lấy dữ liệu
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        val url = "http://10.0.2.2/android/bai1/lab10_2.php" // API PHP của bạn

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                parseJSON(response)
            },
            Response.ErrorListener { error ->
                val statusCode = error.networkResponse?.statusCode ?: "Không rõ"
                Log.e("API_ERROR", "Lỗi khi lấy dữ liệu: ${error.message} | Mã lỗi HTTP: $statusCode")
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    private fun parseJSON(response: JSONArray?) {
        if (response == null || response.length() == 0) {
            Log.e("API_ERROR", "Dữ liệu JSON rỗng hoặc null")
            return
        }

        Log.d("API_RESPONSE", "JSON nhận được: $response") // 🔹 Kiểm tra JSON

        sinhVienList.clear()
        for (i in 0 until response.length()) {
            val item = response.getJSONObject(i)
            val sinhVien = Students(
                item.getInt("ID"),
                item.getString("Name"),
                item.getString("email"),
                item.getString("phone")
            )
            sinhVienList.add(sinhVien)
        }

        Log.d("API_RESPONSE", "Số sinh viên trong danh sách: ${sinhVienList.size}") // 🔹 Kiểm tra danh sách có phần tử không

        studentAdapter.notifyDataSetChanged()
    }








}