
package com.shushant.androidmvvmtask.di

import android.content.Context
import androidx.room.Room
import com.shushant.androidmvvmtask.persistence.AppDatabase
import com.shushant.androidmvvmtask.persistence.DemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
    return Room
      .databaseBuilder(context, AppDatabase::class.java, "Demo.db")
      .allowMainThreadQueries()
      .build()
  }

  @Provides
  @Singleton
  fun provideDao(appDatabase: AppDatabase): DemoDao {
    return appDatabase.demoDao()
  }

}
