package com.example.lab10_1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ RecyclerView và FloatingActionButton từ layout
        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)

        // Thiết lập adapter và layout cho RecyclerView
        postAdapter = PostAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = postAdapter

        // Lấy danh sách bài viết khi ứng dụng khởi động
        getPosts()

        // Xử lý sự kiện khi nhấn nút FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent) // Chuyển sang AddPostActivity
        }
    }

    // Hàm gọi API để lấy danh sách bài viết
    private fun getPosts() {
        ApiClient.api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        postAdapter.submitList(posts) // Hiển thị danh sách bài viết trong RecyclerView
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to get posts", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Làm mới danh sách bài viết khi quay lại từ AddPostActivity
    override fun onResume() {
        super.onResume()
        getPosts() // Lấy lại danh sách bài viết
    }
}
