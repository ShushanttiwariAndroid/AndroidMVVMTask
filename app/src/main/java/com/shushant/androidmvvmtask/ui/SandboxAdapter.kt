package com.shushant.androidmvvmtask.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shushant.androidmvvmtask.databinding.ItemSandboxBinding
import com.shushant.androidmvvmtask.models.SandboxResponseClassItem

class SandboxAdapter :
    ListAdapter<SandboxResponseClassItem, RecyclerView.ViewHolder>(ItemDiffCallback) {

    inner class MyViewHolder(val binding: ItemSandboxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: SandboxResponseClassItem) {
            with(binding) {
                tvResponse.text = "${currentList.size} $item"
            }
            binding.root.setOnClickListener {

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemSandboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(getItem(position))
    }


    object ItemDiffCallback : DiffUtil.ItemCallback<SandboxResponseClassItem>() {
        override fun areItemsTheSame(
            oldItem: SandboxResponseClassItem,
            newItem: SandboxResponseClassItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SandboxResponseClassItem,
            newItem: SandboxResponseClassItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
