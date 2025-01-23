package com.example.productbrowser.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.productbrowser.databinding.ActivityMainBinding
import com.example.productbrowser.ui.addProductFragment.AddProductFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding?= null
    private val binding get() = _binding!!

    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        homeViewModel.getProducts()

        binding.addProductBtn.setOnClickListener{
            val bottomSheet = AddProductFragment()
            bottomSheet.show(supportFragmentManager, "AddProductBottomSheet")

        }

    }
}