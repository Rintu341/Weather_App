package com.example.weather.di

import com.example.weather.network.WeatherApi
import com.example.weather.repository.weatherRepository.WeatherRepository
import com.example.weather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//@InstallIn(SingletonComponent::class)
@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi
            = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
    @Singleton
    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi):WeatherRepository
            = WeatherRepository(weatherApi)

}