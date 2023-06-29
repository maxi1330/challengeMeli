package com.gnovatto.challengemeli.di

import com.gnovatto.challengemeli.data.source.DetailProductApi
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

/**
 * Módulo de inyección de dependencias.
 */
@Module
@InstallIn(SingletonComponent::class)
class Module {

    /**
     * Proporciona un dispatch entrada/salida.
     *
     * @return El dispatch
     */
    @Provides
    @Named("dispatcher")
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Proporciona una instancia de Retrofit
     *
     * @return La instancia de Retrofit.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL_MELI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Proporciona una instancia de ProductSearchApi para realizar búsquedas de productos.
     *
     * @param retrofit La instancia de Retrofit.
     * @return La instancia de ProductSearchApi.
     */
    @Provides
    @Singleton
    fun provideSearchProductApi(retrofit: Retrofit): ProductSearchApi {
        return retrofit.create(ProductSearchApi::class.java)
    }

    /**
     * Proporciona una instancia de DetailProductApi para obtener detalles de productos.
     *
     * @param retrofit La instancia de Retrofit.
     * @return La instancia de DetailProductApi.
     */
    @Provides
    @Singleton
    fun provideDescriptionProductApi(retrofit: Retrofit): DetailProductApi {
        return retrofit.create(DetailProductApi::class.java)
    }

    companion object {
        private const val API_URL_MELI = "https://api.mercadolibre.com"
    }

}