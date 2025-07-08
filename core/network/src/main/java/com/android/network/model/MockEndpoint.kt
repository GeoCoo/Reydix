package com.android.network.model


enum class MockEndpoint(
    val path: String,
    val assetFile: String
) {
    EVENTS(
        path = "/events",
        assetFile = "events.json"
    );

    companion object {
        fun fromPath(path: String): MockEndpoint? =
            entries.find { it.path == path }
    }
}