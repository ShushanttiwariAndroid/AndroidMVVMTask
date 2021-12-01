
package com.shushant.androidmvvmtask.di

import com.shushant.androidmvvmtask.network.Api
import com.shushant.androidmvvmtask.network.RequestInterceptor
import com.shushant.androidmvvmtask.network.service.DemoClient
import com.shushant.androidmvvmtask.network.service.DemoService

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okhHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okhHttpClient)
      .baseUrl(Api.BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideDemoService(retrofit: Retrofit): DemoService {
    return retrofit.create(DemoService::class.java)
  }

  @Provides
  @Singleton
  fun provideDemoClient(demoService: DemoService) = DemoClient(demoService)
}
