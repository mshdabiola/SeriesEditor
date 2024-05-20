package com.mshdabiola.datastore.di

import android.content.Context
import com.mshdabiola.datastore.createDataStoreUserData
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

actual val datastoreModule: Module
    get() = module {
        includes(commonModule)

        single(qualifier = qualifier("userdata")) {
            val context: Context = get()

            createDataStoreUserData { context.filesDir.resolve("userdata").absolutePath }
        }
    }
