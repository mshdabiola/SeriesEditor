package com.mshdabiola.data.repository

import com.mshdabiola.seriesmodel.Content
import com.mshdabiola.seriesmodel.ExaminationWithSubject
import com.mshdabiola.seriesmodel.Option
import com.mshdabiola.seriesmodel.Question
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.FileOutputStream


class ExportWord(
    private val examination: ExaminationWithSubject, private val questions: List<Question>,
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

            addContent(instruction.content)
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
            //run.addBreak()
            run.setText("\n")
        }
    }

    private fun addContent(contents: List<Content>) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()
        for (content in contents) {
            run.setText(content.content)
            run.setText(" ")
        }

    }

    private fun addQuestionContent(contents: List<Content>, number: Int) {
        val paragraph = document.createParagraph()
        val run = paragraph.createRun()
        run.setText(" $number. ")
        for (content in contents) {
            run.setText(content.content)
        }

    }

    private fun addOptionContent(options: List<Option>) {


        options.forEachIndexed { index, option ->

            val paragraph = document.createParagraph()
            val run = paragraph.createRun()
            run.setText("   ")


            run.setText("(${('A' + index)}) ")

            for (content in option.contents) {

                run.setText(content.content)
                run.setText(" ")
            }

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
