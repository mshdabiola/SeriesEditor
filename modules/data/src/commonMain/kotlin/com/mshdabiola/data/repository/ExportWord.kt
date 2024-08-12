package com.mshdabiola.data.repository

import androidx.compose.ui.graphics.Color
import com.mshdabiola.model.ImageUtil
import com.mshdabiola.serieslatex.getLatexImage
import com.mshdabiola.serieslatex.toByteArray
import com.mshdabiola.seriesmodel.Content
import com.mshdabiola.seriesmodel.ExaminationWithSubject
import com.mshdabiola.seriesmodel.Option
import com.mshdabiola.seriesmodel.Question
import com.mshdabiola.seriesmodel.Type
import org.apache.poi.util.Units
import org.apache.poi.xwpf.usermodel.Document
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFRun
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.FileOutputStream

class ExportWord(
    private val examination: ExaminationWithSubject,
    private val questions: List<Question>,
) {
    private val document = XWPFDocument()

    private fun process() {
        createHead("Examination")
        addParagraph("subject: ${examination.subject.title}")
        addParagraph("Year: ${examination.examination.year}")
        addParagraph("Duration: ${examination.examination.duration} minutes")
        addBreak(1)
        val instructions = questions.mapNotNull { it.instruction }
        if (instructions.isNotEmpty()) {
            createSubHead("Instructions")
        }
        instructions.forEachIndexed { index, instruction ->
            addParagraph(instruction.title, index + 1)
            val paragraph = document.createParagraph()
            val run = paragraph.createRun()
            addContent(run, instruction.content)
            addBreak(1)
        }
        addBreak(1)
        createSubHead("Questions")

        questions.forEachIndexed { index, question ->

            if (question.instruction != null) {
                val index = questions.indexOf(question) + 1
                addParagraphItalic("Use instruction $index to answer the question")
            }
            addQuestionContent(question.contents, number = index + 1)
            val options = question.options
            if (options != null) {
                addOptionContent(options)
            }
            addBreak(1)
        }
    }

    fun write(path: String): Boolean {
        process()

        val outputStream = FileOutputStream(path)
        document.write(outputStream)
        outputStream.close()

        println("Letter created successfully!")

        return true
    }

    private fun createHead(header: String) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()
        paragraph.alignment = ParagraphAlignment.CENTER
        run.setBold(true)
        run.setText(header)
        run.setFontSize(24) // Adjust font size as needed
    }

    private fun createSubHead(header: String) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()
        run.setBold(true)
        run.setText(header)
        run.setFontSize(16) // Adjust font size as needed
    }

    private fun addBreak(number: Int = 1) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()
        repeat(number) {
            // run.addBreak()
            run.setText("\n")
        }
    }

    private fun addContent(run: XWPFRun, contents: List<Content>) {
        for (content in contents) {
            when (content.type) {
                Type.TEXT -> {
                    run.setText(content.content)
                    run.setText(" ")
                }

                Type.IMAGE -> {
                    val imageData =
                        FileInputStream(ImageUtil.getAppPath(content.content).path).readAllBytes()
                    addImageToWord(run, imageData, 200.0, 200.0)
                }

                Type.EQUATION -> {
                    val imageData = getLatexImage(
                        content.content,
                        backgroundColor = Color.Transparent,
                        foregroundColor = Color.Black,
                    )
                        .toByteArray()
                    addImageToWord(run, imageData, 300.0, 50.0)
                }
            }
        }
    }

    private fun addQuestionContent(contents: List<Content>, number: Int) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()
        run.setText(" $number. ")
        for (content in contents) {
            when (content.type) {
                Type.TEXT -> {
                    run.setText(content.content)
                }

                Type.IMAGE -> {
                    val imageData =
                        FileInputStream(ImageUtil.getAppPath(content.content).path).readAllBytes()
                    addImageToWord(run, imageData, 200.0, 200.0)
                }

                Type.EQUATION -> {
                    val imageData = getLatexImage(
                        content.content,
                        backgroundColor = Color.Transparent,
                        foregroundColor = Color.Black,
                    )
                        .toByteArray()
                    addImageToWord(run, imageData, 300.0, 50.0)
                }
            }
        }
    }

    fun addImageToWord(
        run: org.apache.poi.xwpf.usermodel.XWPFRun,
        imageData: ByteArray,
        width: Double,
        height: Double,
    ) {
        // Load the image

        // Create a relationship for the image
        val relationshipId =
            document.addPictureData(ByteArrayInputStream(imageData), Document.PICTURE_TYPE_PNG)

        // Add the image to the run
        run.addPicture(
            ByteArrayInputStream(imageData),
            Document.PICTURE_TYPE_PNG,
            relationshipId,
            Units.toEMU(width),
            Units.toEMU(height),
        )
        run.addBreak()
    }

    private fun addOptionContent(options: List<Option>) {
        options.forEachIndexed { index, option ->

            val paragraph = document.createParagraph()
            val run = paragraph.createRun()
            run.setText("   ")

            run.setText("(${('A' + index)}) ")

            addContent(run, option.contents)
        }
    }

    private fun addParagraph(text: String, index: Int? = null) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()

        if (index != null) {
            run.setText("$index. ")
        }
        run.setText(text)
    }

    private fun addParagraphItalic(text: String) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()
        run.isItalic = true
        run.setText(text)
        run.setFontSize(10)
    }
}
