package com.example.lab10_2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter (private val sinhVienList: List<Students>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val tv_id = view.findViewById<TextView>(R.id.tv_id)
         val tv_hoten = view.findViewById<TextView>(R.id.tv_hoten)
         val tv_email = view.findViewById<TextView>(R.id.tv_email)
         val tv_sdt = view.findViewById<TextView>(R.id.tv_sdt)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("RecyclerView", "Số lượng item trong adapter: ${sinhVienList.size}")
        return sinhVienList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val sinhVien = sinhVienList[position]
        Log.d("RecyclerView", "Đang hiển thị: ID=${sinhVien.ID}, Name=${sinhVien.Name}")

        holder.tv_id.text = sinhVien.ID.toString()
        holder.tv_hoten.text = sinhVien.Name
        holder.tv_email.text = sinhVien.email
        holder.tv_sdt.text = sinhVien.phone
    }

}