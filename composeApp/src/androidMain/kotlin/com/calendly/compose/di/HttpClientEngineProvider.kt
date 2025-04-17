package com.calendly.compose.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

internal actual object HttpClientEngineProvider {
    actual fun provide(): HttpClientEngine {
        return OkHttp.create()
    }
}
