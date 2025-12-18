package com.example.pertemuan12.repositori

import android.app.Application
import com.example.pertemuan12.apiservice.JaringanRepositoryDataSiswa
import com.example.pertemuan12.apiservice.ServiceApiSiswa // Pastikan ini diimport
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

// 1. Interface Container
interface ContainerApp {
    val repositoryDataSiswa: RepositoryDataSiswa
}

// 2. Implementasi Container
class DefaultContainerApp : ContainerApp {
    private val baseurl = "http://10.0.2.2/umyTI/"

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.Body
    }

    private val klien = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(klien)
        .build()

    // Pastikan interface ServiceApiSiswa sudah Anda buat di package apiservice
    private val retrofitService: ServiceApiSiswa by lazy {
        retrofit.create(ServiceApiSiswa::class.java)
    }

    override val repositoryDataSiswa: RepositoryDataSiswa by lazy {
        JaringanRepositoryDataSiswa(retrofitService)
    }
}

// 3. Kelas Aplikasi (Diletakkan di luar kelas DefaultContainerApp)
class AplikasiDataSiswa : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainerApp()
    }
}