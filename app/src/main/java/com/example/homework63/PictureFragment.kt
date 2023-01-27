package com.example.homework63

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework63.databinding.FragmentPictureBinding

class PictureFragment : Fragment() {

    private lateinit var binding: FragmentPictureBinding
    private val adapter = PictureAdapter(this::onClick)
    private val imgList = arrayListOf<ImgModel>()
    private val imgTest = arrayListOf<String>()
    private val imgSend = arrayListOf<String>()

    companion object {
        const val CODE_FOR_PICTURES = "key2"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        adapter.addImg(imgList)
        binding.btnAdd.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSend.setOnClickListener {
            adapter.getPicked(false)
            findNavController().navigate(
                R.id.recievedFragment,
                bundleOf(CODE_FOR_PICTURES to imgSend)
            )
        }
    }

    private fun init() {
        binding.apply {
            imgRecycler.layoutManager = GridLayoutManager(context, 3)
            imgRecycler.adapter = adapter
        }
    }

    private fun getData() {
        arguments?.let {
            val value = it.getStringArrayList(FirstFragment.CODE_FOR_RECIEVED)
            if (value != null) {
                imgList.clear()
                imgTest.clear()
                imgTest.addAll(value)
                var i = 0
                while (i < imgTest.size) {
                    imgList.add(ImgModel(imgTest[i]))
                    i++
                }
            }
        }
    }

    private fun onClick(img: String) {
        imgSend.add(img)
    }

}