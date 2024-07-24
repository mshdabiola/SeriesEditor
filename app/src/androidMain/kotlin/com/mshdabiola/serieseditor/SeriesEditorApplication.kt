/*
 *abiola 2024
 */

package com.mshdabiola.serieseditor

import android.app.Application
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.model.parentPath
import com.mshdabiola.serieseditor.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SeriesEditorApplication : Application() {

    override fun onCreate() {
        super.onCreate()



        parentPath = this.applicationContext.filesDir.path

        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            androidContext(this@SeriesEditorApplication)
            modules(appModule)
        }

//        if (packageName.contains("debug")) {
//            Timber.plant(Timber.DebugTree())
//            Timber.e("log on app create")
//        }
    }
}
