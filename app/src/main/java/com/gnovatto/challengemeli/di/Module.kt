package com.gnovatto.challengemeli.di

import com.gnovatto.challengemeli.data.source.DescriptionProductApi
import com.gnovatto.challengemeli.data.source.ProductSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Named("dispatcher")
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL_MELI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchProductApi(retrofit: Retrofit): ProductSearchApi {
        return retrofit.create(ProductSearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDescriptionProductApi(retrofit: Retrofit): DescriptionProductApi {
        return retrofit.create(DescriptionProductApi::class.java)
    }

    companion object {
        private const val API_URL_MELI = "https://api.mercadolibre.com"
    }

}