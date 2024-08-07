package com.mshdabiola.datastore.di

import android.content.Context
import com.mshdabiola.datastore.createDataStoreInstruction
import com.mshdabiola.datastore.createDataStoreQuestion
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

        single(qualifier = qualifier("question")) {
            val context: Context = get()

            createDataStoreQuestion { context.filesDir.resolve("question").absolutePath }
        }

        single(qualifier = qualifier("instruction")) {
            val context: Context = get()

            createDataStoreInstruction { context.filesDir.resolve("instruction").absolutePath }
        }
    }
