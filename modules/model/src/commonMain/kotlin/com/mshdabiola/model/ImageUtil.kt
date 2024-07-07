package com.mshdabiola.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object ImageUtil {

    private fun newPath(extension: String, examId: Long): File {
        val time = System.currentTimeMillis()
        // val extension = if (extension == "svg") "svg" else extension
        return (getAppPath("$examId/$time.$extension"))
    }

//    fun getGeneralDir(name: String, examId: Long): File {
//        return File(generalPath, "image/$examId/$name")
//    }

    fun getAppPath(name: String): File {
        return File(generalPath, "image/$name")
    }

    suspend fun saveImage(
        oldName: String, // old image
        fileString: String, // new image
        examId: Long,

    ): String {
        return withContext(Dispatchers.IO) {
            val oldPath = File(getAppPath("$examId/$oldName").path)
            oldPath.delete()
            val imageFile = File(fileString)
            val newPath = newPath(imageFile.extension, examId)

//            if (imageFile.extension == "svg") {
//                imageFile.copyTo(newPath)
//
//            } else {
            imageFile.copyTo(newPath)
//            }

            newPath.name
        }
    }
}
