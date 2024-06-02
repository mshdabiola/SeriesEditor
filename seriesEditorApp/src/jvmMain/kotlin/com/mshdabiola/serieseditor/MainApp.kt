package com.mshdabiola.serieseditor

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.database.generalPath
import com.mshdabiola.designsystem.drawable.defaultAppIcon
import com.mshdabiola.designsystem.string.appName
import com.mshdabiola.model.Writer
import com.mshdabiola.serieseditor.di.appModule
import com.mshdabiola.serieseditor.ui.SeriesEditorApp
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import java.io.File

fun mainApp() {
    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )

        val version = "0.0.1"
        Window(
            onCloseRequest = ::exitApplication,
            title = "$appName v$version",
            icon = defaultAppIcon,
            state = windowState,
        ) {
            SeriesEditorApp()
        }
    }
}

fun main() {
    val path = File("${System.getProperty("user.home")}/AppData/Local/hydraulic")
    generalPath = com.mshdabiola.model.generalPath

    if (path.exists().not()) {
        path.mkdirs()
    }
    val logger = Logger(
        loggerConfigInit(platformLogWriter(), Writer(path)),
        "DesktopLogger,",
    )
    val logModule = module {
        single {
            logger
        }
    }

    try {
        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            modules(
                appModule,
                logModule,
            )
        }

        mainApp()
    } catch (e: Exception) {
        logger.e("crash exceptions", e)
        throw e
    }
}
