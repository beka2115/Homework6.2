package com.example.homework63.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework63.databinding.ItemRecievedBinding

class RecievedAdapter : RecyclerView.Adapter<RecievedAdapter.RecievedViewHolder>() {

    private val imgList: ArrayList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecievedViewHolder {
        return RecievedViewHolder(
            ItemRecievedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
       return imgList.size
    }

    override fun onBindViewHolder(holder: RecievedViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addImg(newImg: ArrayList<String>) {
        this.imgList.clear()
        this.imgList.addAll(newImg)
        notifyDataSetChanged()
    }

    inner class RecievedViewHolder(private val binding: ItemRecievedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(img: String) {
            Glide.with(binding.imgRecyclerRecived).load(img).into(binding.imgRecyclerRecived)
        }
    }
}