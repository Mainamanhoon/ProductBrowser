package com.example.productbrowser.ui.home.ListProductFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common.Resource
import com.example.productbrowser.databinding.FragmentListProductBinding
import com.example.productbrowser.ui.home.HomeViewModel
import com.example.productbrowser.ui.home.ProductsAdapter
import com.example.productbrowser.ui.home.ProductComparator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListProductFragment : Fragment() {
    var _binding : FragmentListProductBinding? = null
    val binding get() = _binding!!
    val viewModel : HomeViewModel by activityViewModels()
    val pagingAdapter = ProductsAdapter(ProductComparator)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListProductBinding.inflate(inflater, container, false)
        setRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addProductState.collectLatest { result ->
                when (result) {
                    is Resource.Error ->{
                        Log.d("AddProductsUsecase","Error: \${result.exception.message")
                    }
                    is Resource.Loading ->Unit

                    is Resource.Success ->{
                        pagingAdapter.refresh()
                    }
                }
            }
        }


        binding.swipeToRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
            lifecycleScope.launch {
                binding.swipeToRefresh.isRefreshing=false
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectProducts()



        binding.searchEt.addTextChangedListener{editable->
            val searchQuery = editable.toString().trim()
            if(searchQuery.isNullOrBlank())binding.cleartextBtn.visibility=View.INVISIBLE
            else binding.cleartextBtn.visibility=View.VISIBLE
            collectSearchFlow(searchQuery)
        }

        binding.cleartextBtn.setOnClickListener {
            binding.searchEt.text.clear()
        }

    }



    private fun collectSearchFlow(searchQuery: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchProducts(searchQuery).collect{ pagingData ->
                 pagingAdapter.submitData(pagingData)
            }
        }
     }

    fun setRecyclerView() {
        val recyclerView = binding.productsGv
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = pagingAdapter
    }

    fun collectProducts(){

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pager.collectLatest { pagingData ->
//                Log.d("DEBUG_PAGING", "Received PagingData: $pagingData")
//                 Convert PagingData to a Snapshot for Debugging
//                val snapshot = pagingAdapter.snapshot().items
//                Log.d("DEBUG_PAGING_ITEMS", "Items in PagingData: ${snapshot.size}")
                pagingAdapter.submitData(pagingData)

            }
        }
    }


}