package com.shushant.androidmvvmtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.shushant.androidmvvmtask.databinding.ActivityMainBinding
import com.shushant.androidmvvmtask.utils.SwipeToDeleteCallback
import com.skydoves.whatif.whatIfNotNullOrEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
     val viewModel: MainViewModel by viewModels()
    private val sandboxAdapter by lazy { SandboxAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
        _binding?.sandboxRv?.apply {
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
            adapter = sandboxAdapter
        }
        observeData()
        enableSwipeToDelete()
    }

    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteItemFromDb(sandboxAdapter.currentList.get(viewHolder.absoluteAdapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(_binding?.sandboxRv)

    }

    private fun observeData() {
        viewModel.data.observe(this) { list ->
            list.whatIfNotNullOrEmpty {
                sandboxAdapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}