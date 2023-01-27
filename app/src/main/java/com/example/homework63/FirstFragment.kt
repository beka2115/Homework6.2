package com.example.homework63

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework63.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var listUri = arrayListOf<String>()
    private lateinit var binding: FragmentFirstBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var selectImagesActivityResult: ActivityResultLauncher<Intent>

    companion object {
        const val CODE_FOR_INTENT = "image/*"
        const val CODE_FOR_RECIEVED = "key"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerActivity()
        registerPermission()
        binding.btnFirst.setOnClickListener {
            checkCameraPermission()
            permissionLauncher.launch(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
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
                            val imageUri: Uri? = data.clipData?.getItemAt(i)?.uri
                            listUri.add(imageUri.toString())
                        }
                        findNavController().navigate(
                            R.id.pictureFragment, bundleOf(
                                CODE_FOR_RECIEVED to listUri
                            )
                        )
                    } else if (data?.data != null) {
                        val imageUri: Uri? = data.data
                        listUri.add(imageUri.toString())
                        findNavController().navigate(
                            R.id.pictureFragment, bundleOf(
                                CODE_FOR_RECIEVED to listUri
                            )
                        )
                    }
                }
            }
    }

    private fun checkCameraPermission() {
        when {
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
                    != PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(context, "Give your permission", Toast.LENGTH_LONG).show()
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