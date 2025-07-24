package com.edurda77.test.workmate.di

import androidx.room.Room
import com.edurda77.test.workmate.data.local.CasheDatabase
import com.edurda77.test.workmate.domain.utils.BASE_URL
import com.edurda77.test.workmate.domain.utils.CASHE_DB
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val baseModule = module {

    single<HttpClient> {
        HttpClient(OkHttp) {
            install(HttpTimeout) {
                connectTimeoutMillis = 100000
                requestTimeoutMillis = 100000
                socketTimeoutMillis = 100000
            }
            defaultRequest {
                url(BASE_URL)
            }
            install(Logging) {
                logger = Logger.DEFAULT
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    single<CasheDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CasheDatabase::class.java,
            CASHE_DB
        )
            .build()
    }
}