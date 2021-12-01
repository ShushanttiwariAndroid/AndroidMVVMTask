
package com.shushant.androidmvvmtask.di

import com.shushant.androidmvvmtask.network.service.DemoClient
import com.shushant.androidmvvmtask.network.service.DemoService
import com.shushant.androidmvvmtask.persistence.DemoDao
import com.shushant.androidmvvmtask.repository.DemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideDemoRepository(
    demoClient: DemoClient,
    demoDao: DemoDao
  ): DemoRepository {
    return DemoRepository(demoClient, demoDao)
  }

}
