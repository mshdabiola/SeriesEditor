package com.mshdabiola.model

import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Writer(private val path: File) : LogWriter() {
    private val filePath: File by lazy {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formatter.format(Date())
        File(
            File(path, "log").apply {
                if (this.exists().not()) {
                    mkdirs()
                }
            },
            "log-$date.txt",
        )
    }

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        val messageStr = DefaultFormatter.formatMessage(severity, Tag(tag), Message(message))
        saveLogsToTxtFile("$messageStr \n")
        if (throwable != null) {
            saveLogsToTxtFile("\n\n ${throwable.localizedMessage}")
        }
    }

    private fun saveLogsToTxtFile(message: String) {
        val coroutineCallLogger = CoroutineScope(Dispatchers.IO)
        coroutineCallLogger.launch {
            runCatching {
                if (filePath.exists().not()) {
                    filePath.createNewFile()
                }
                // Writing my logs to txt file.
                filePath.appendText(
                    message,
                )
            }
        }
    }
}
