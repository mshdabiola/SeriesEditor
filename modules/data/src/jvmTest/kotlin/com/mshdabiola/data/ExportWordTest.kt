package com.mshdabiola.data

import com.mshdabiola.data.repository.ExportWord
import com.mshdabiola.seriesmodel.ExaminationWithSubject
import com.mshdabiola.testing.examinations
import com.mshdabiola.testing.questions
import com.mshdabiola.testing.series
import com.mshdabiola.testing.subjects
import java.io.File
import kotlin.test.Test

class ExportWordTest {

    fun export() {
        val textToExport = "This is the text to be exported to a Word document."
        val examinationWithSubject = ExaminationWithSubject(
            examination = examinations.first(),
            subject = subjects.first(),
            series = series.first(),
        )
        val exportWord = ExportWord(examination = examinationWithSubject, questions = questions)
        val path = "/home/mshdabiola/StudioProjects/SeriesEditor/output/exported_document.docx"
        val file = File(path)
        file.createNewFile()
        exportWord.write(file.absolutePath)
    }
}