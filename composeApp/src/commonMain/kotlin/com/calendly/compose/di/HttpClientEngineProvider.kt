package com.calendly.compose.di

import io.ktor.client.engine.HttpClientEngine

internal expect object HttpClientEngineProvider {
    fun provide(): HttpClientEngine
}
