package com.example.homework63.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework63.databinding.ItemImgBinding
import com.example.homework63.model.ImgModel

class PictureAdapter(
    val onClick: (position: String, click: Boolean) -> Unit
) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    private val imgList: ArrayList<ImgModel> = arrayListOf()
    private var isClicked: Boolean? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            ItemImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPicked(visible: Boolean) {
        isClicked = visible
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addImg(newImg: List<ImgModel>) {
        this.imgList.clear()
        this.imgList.addAll(newImg)
        notifyDataSetChanged()
    }

    inner class PictureViewHolder(private val binding: ItemImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(img: ImgModel) {
            itemView.setOnClickListener {

                if(!img.isClicked){
                    img.isClicked = checkClick(img.isClicked)
                    setVisibility(img.isClicked)
                    img.img?.let { it1 -> onClick(it1,false) }
                }else if (img.isClicked){
                    img.isClicked = checkClick(img.isClicked)
                    setVisibility(img.isClicked)
                    img.img?.let { it1 -> onClick(it1,true) }
                }
            }

            if (isClicked == false) {
                img.isClicked=false
                binding.imgRecyclerAfter.isVisible = false
            }

            Glide.with(binding.imgRecycler).load(img.img).into(binding.imgRecycler)
        }

        private fun checkClick(isItClicked: Boolean): Boolean {
            var answer = false
            if (!isItClicked) {
                answer = true
            }
            if (isItClicked) {
                answer = false
            }
            return answer
        }

        private fun setVisibility(answer: Boolean) {
            if (!answer) {
                binding.imgRecyclerAfter.isVisible = false
            }
            if (answer) {
                binding.imgRecyclerAfter.isVisible = true
            }
        }
    }
}