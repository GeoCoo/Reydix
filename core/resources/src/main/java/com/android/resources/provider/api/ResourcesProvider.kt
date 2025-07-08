package com.android.resources.provider.api

interface ResourceProvider {
    fun getString(resId: Int): String
    fun genericErrorMessage(): String
}