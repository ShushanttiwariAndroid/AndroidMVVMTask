package com.shushant.androidmvvmtask.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shushant.androidmvvmtask.models.DemoClass
import com.shushant.androidmvvmtask.models.SandboxResponseClassItem

@Database(
  entities = [SandboxResponseClassItem::class],
  version = 2, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
  abstract fun demoDao(): DemoDao
}
