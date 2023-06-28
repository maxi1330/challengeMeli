package com.gnovatto.challengemeli.di

import com.gnovatto.challengemeli.data.dataSource.api.DescriptionProductApi
import com.gnovatto.challengemeli.data.dataSource.api.ProductSearchApi
import com.gnovatto.challengemeli.data.dataSource.api.ItemProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.converter.gson.GsonConverterFactory;
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {
    val API_URL_MELI = "https://api.mercadolibre.com"
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
    fun provideCurrenciesApi(retrofit: Retrofit): ItemProductApi {
        return retrofit.create(ItemProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDescriptionProductApi(retrofit: Retrofit): DescriptionProductApi {
        return retrofit.create(DescriptionProductApi::class.java)
    }

}