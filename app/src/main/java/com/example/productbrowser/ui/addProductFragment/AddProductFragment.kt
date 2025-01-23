package com.example.productbrowser.ui.addProductFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.productbrowser.R
import com.example.productbrowser.databinding.FragmentAddProductBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddProductFragment : BottomSheetDialogFragment() {
    var _binding : FragmentAddProductBinding?=null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(layoutInflater)
        return binding.root
    }


}