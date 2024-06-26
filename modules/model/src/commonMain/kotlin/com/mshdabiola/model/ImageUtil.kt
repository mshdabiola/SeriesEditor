package com.mshdabiola.model

import java.io.File

object ImageUtil {

    fun newPath(extension: String, examId: Long): File {
        val time = System.currentTimeMillis()
        // val extension = if (extension == "svg") "svg" else extension
        return (getGeneralDir("$time.$extension", examId))
    }

    fun getGeneralDir(name: String, examId: Long): File {
        return File(generalPath, "image/$examId/$name")
    }
}
