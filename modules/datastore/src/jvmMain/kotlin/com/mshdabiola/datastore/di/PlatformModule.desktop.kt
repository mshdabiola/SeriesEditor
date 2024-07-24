package com.mshdabiola.datastore.di

import com.mshdabiola.datastore.createDataStoreInstruction
import com.mshdabiola.datastore.createDataStoreQuestion
import com.mshdabiola.datastore.createDataStoreUserData
import com.mshdabiola.model.generalPath
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

var storePath= generalPath
actual val datastoreModule: Module
    get() = module {
        includes(commonModule)

        single(qualifier = qualifier("userdata")) {
            createDataStoreUserData { "$storePath/userdataE" }
        }

        single(qualifier = qualifier("question")) {
            createDataStoreQuestion { "$storePath/questions" }
        }

        single(qualifier = qualifier("instruction")) {
            createDataStoreInstruction { "$storePath/instructions" }
        }
    }
