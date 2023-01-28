package com.example.homework63.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework63.R
import com.example.homework63.adapter.PictureAdapter
import com.example.homework63.databinding.FragmentPictureBinding
import com.example.homework63.model.ImgModel

class PictureFragment : Fragment() {

    private lateinit var binding: FragmentPictureBinding
    private val adapter = PictureAdapter(this::onClick)
    private val imgList = arrayListOf<ImgModel>()
    private val imgSend = arrayListOf<String>()
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var selectImagesActivityResult: ActivityResultLauncher<Intent>
    companion object {
        const val CODE_FOR_PICTURES = "key2"
        const val CODE_FOR_INTENT = "image/*"
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
        initClicker()
        registerActivity()
        registerPermission()
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        (activity as AppCompatActivity).supportActionBar?.title = "Выбор фотографий"
        imgSend.clear()
    }

    private fun init() {

        binding.apply {
            imgRecycler.layoutManager = GridLayoutManager(context, 3)
            imgRecycler.adapter = adapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onClick(img: String, check: Boolean) {
        if (!check) {
            imgSend.add(img)
        }
        if (check) {
            imgSend.remove(img)
        }
        if (imgSend.size > 0) {
            binding.cardCount.isVisible = true
            binding.textCount.text = "Выбрано ${imgSend.size} фотографий"
        } else {
            binding.cardCount.isVisible = false
        }
    }

    private fun initClicker() {
        binding.btnAdd.setOnClickListener {
            adapter.getPicked(false)
            imgSend.clear()
            permissionLauncher.launch(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            binding.cardCount.isVisible = false
        }

        binding.btnSend.setOnClickListener {
            adapter.getPicked(false)
            findNavController().navigate(
                R.id.recievedFragment,
                bundleOf(CODE_FOR_PICTURES to imgSend)
            )
        }
    }

    private fun registerPermission() {
        permissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { result ->
                if (result) {
                    getImage()
                } else {
                    Toast.makeText(context, "Give your permission", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun registerActivity() {
        selectImagesActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data?.clipData != null) {
                        val count = data.clipData?.itemCount ?: 0
                        for (i in 0 until count) {
                            imgList.add(ImgModel(data.clipData?.getItemAt(i)?.uri.toString()))
                        }
                        adapter.addImg(imgList)
                        imgList.clear()


                    } else if (data?.data != null) {
                        val imageUri: Uri? = data.data
                        imgList.add(ImgModel(imageUri.toString()))
                        adapter.addImg(imgList)
                        imgList.clear()
                    }
                }
            }
    }

    private fun getImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = CODE_FOR_INTENT
        selectImagesActivityResult.launch(intent)
    }

}