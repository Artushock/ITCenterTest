package com.artushock.itcentertest.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.artushock.itcentertest.repository.WeatherRepository
import com.artushock.itcentertest.repository.WeatherRepositoryImpl
import com.artushock.itcentertest.repository.retrofit.WeatherService
import com.artushock.itcentertest.repository.room.WeatherQueriesDataBase
import com.artushock.itcentertest.ui.search.SearchFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun context(): Context
    fun applicationContext(): Application

    fun inject(fragment: SearchFragment)
}

@Module(
    includes = [
        NetworkModule::class,
        DataBaseModule::class,
        AppBindModule::class
    ]
)
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application
}

@Module
class NetworkModule {

    @Provides
    fun provideWeatherService(client: OkHttpClient): WeatherService {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
            .create(WeatherService::class.java)
    }

    @Provides
    fun provideNetworkClient(
        loggingInterceptor: HttpLoggingInterceptor,
        baseInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(baseInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideBaseInterceptor(): Interceptor = Interceptor.invoke { chain ->
        val newUrl = chain
            .request()
            .url
            .newBuilder()
            .build()

        val request = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()

        return@invoke chain.proceed(request)
    }
}

@Module
class DataBaseModule() {

    @Singleton
    @Provides
    fun provideDB(app: Application): WeatherQueriesDataBase = Room.databaseBuilder(
        app,
        WeatherQueriesDataBase::class.java,
        "database.db"
    ).build()
}

@Module
interface AppBindModule {

    @Binds
    fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
