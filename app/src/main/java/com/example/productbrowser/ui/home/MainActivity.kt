package com.example.productbrowser.ui.home

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.BackoffPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.worker.ProductWorker
import com.example.productbrowser.databinding.ActivityMainBinding
import com.example.productbrowser.ui.home.addProductFragment.AddProductFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding?= null
    private val binding get() = _binding!!

    val homeViewModel: HomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val workRequest = PeriodicWorkRequestBuilder<ProductWorker>(15,TimeUnit.MINUTES)
//            .setInitialDelay(Duration.ofSeconds(10))
//            .setBackoffCriteria(backoffPolicy = BackoffPolicy.LINEAR,
//                                duration = Duration.ofSeconds(10))
//            .build()
//        WorkManager.getInstance(applicationContext).enqueue(workRequest)


        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        homeViewModel.getProducts()

        binding.addProductBtn.setOnClickListener{
            val bottomSheet = AddProductFragment()
            bottomSheet.show(supportFragmentManager, "AddProductBottomSheet")

        }

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}