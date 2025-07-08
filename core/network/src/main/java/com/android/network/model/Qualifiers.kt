package com.android.network.model

import jakarta.inject.Qualifier

class Qualifiers {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Real

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Mock
}