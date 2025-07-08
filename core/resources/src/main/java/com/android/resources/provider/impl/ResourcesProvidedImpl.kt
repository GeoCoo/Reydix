package com.android.resources.provider.impl

import android.content.Context
import com.android.resources.provider.api.ResourceProvider
import com.android.reydix.core.resources.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun genericErrorMessage(): String = context.getString(R.string.generioc_error_msg)
}