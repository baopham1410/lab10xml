package com.example.lab10_1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPostActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var bodyEditText: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        // Ánh xạ các thành phần giao diện
        titleEditText = findViewById(R.id.titleEditText)
        bodyEditText = findViewById(R.id.bodyEditText)
        addButton = findViewById(R.id.addButton)

        // Xử lý sự kiện khi nhấn nút "Add Post"
        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val body = bodyEditText.text.toString()

            if (title.isNotEmpty() && body.isNotEmpty()) {
                createPost(title, body)
            } else {
                Toast.makeText(this, "Title and body cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Gọi API để thêm bài viết mới
    private fun createPost(title: String, body: String) {
        val newPost = Post(userId = 1, id = 0, title = title, body = body)

        ApiClient.api.createPost(newPost).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddPostActivity, "Post created!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddPostActivity, "Failed to create post", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@AddPostActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
