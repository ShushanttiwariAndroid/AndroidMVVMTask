package com.shushant.androidmvvmtask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DemoClass(@PrimaryKey(autoGenerate = true) val id: Int=1)
