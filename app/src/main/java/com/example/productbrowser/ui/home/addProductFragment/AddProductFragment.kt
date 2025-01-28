package com.example.productbrowser.ui.home.addProductFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.models.Product
import com.example.productbrowser.databinding.FragmentAddProductBinding
import com.example.productbrowser.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class AddProductFragment : BottomSheetDialogFragment() {
    var _binding : FragmentAddProductBinding?=null
    val binding get() = _binding!!
    val viewModel : HomeViewModel by activityViewModels()
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(layoutInflater)

        var imageUri : Uri? = null
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                 imageUri = intent?.data
                 if (imageUri != null) {
                    binding.ivProductImage.setImageURI(imageUri)
                } else {
//                    Toast.makeText(context, "No image Selected", Toast.LENGTH_LONG).show()
                }
            }

        }
        binding.btnClose.setOnClickListener{
            dismiss()
        }

        binding.cardViewImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResultLauncher.launch(intent)
        }

        binding.btnSaveProduct.setOnClickListener{
            val productName = binding.etProductName.text.toString().trim()
            val productType = binding.etProductType.text.toString().trim()
            val price = binding.etSellingPrice.text.toString().trim().toDoubleOrNull()?:-1.0
            val tax = binding.etTaxRate.text.toString().trim().toDoubleOrNull()?:-1.0

            if(productType.isNullOrBlank() || productName.isNullOrBlank() || price==-1.0|| tax==-1.0){
                Toast.makeText(context , "Enter the required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val tempfile = imageUri?.let { it1 -> getFileFromUri(requireContext(), it1) }
            var path :String? = null
            tempfile?.let { path = it.absolutePath }


            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addProduct(Product(
                    product_type = productType,
                    product_name = productName,
                    price = price!!,
                    tax = tax!!,
                    image = path?:""
                ))
             }

            dismiss()
        }
        return binding.root
    }
    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver

        val fileExtension = MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(contentResolver.getType(uri)) ?: "jpg"

        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.$fileExtension")

        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }




}