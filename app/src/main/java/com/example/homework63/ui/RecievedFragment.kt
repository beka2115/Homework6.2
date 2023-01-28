package com.example.homework63.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework63.adapter.RecievedAdapter
import com.example.homework63.databinding.FragmentRecievedBinding


class RecievedFragment : Fragment() {

    private lateinit var binding: FragmentRecievedBinding
    private val adapter = RecievedAdapter()
    private val imgList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecievedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        initClicker()
        (activity as AppCompatActivity).supportActionBar?.title = "Выбранные фотографии"
    }

    private fun initClicker() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getData() {
        arguments?.let {
            val value = it.getStringArrayList(PictureFragment.CODE_FOR_PICTURES)
            if (value != null) {
                imgList.clear()
                imgList.addAll(value)
                adapter.addImg(imgList)
            }
        }
    }

    private fun init() {
        binding.apply {
            imgRecyclerRecived.layoutManager = GridLayoutManager(context, 3)
            imgRecyclerRecived.adapter = adapter
        }
    }

}