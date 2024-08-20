package com.mshdabiola.data.repository

import com.mshdabiola.seriesmodel.ExaminationWithSubject
import com.mshdabiola.seriesmodel.Question

expect fun toWord(path: String, examination: ExaminationWithSubject, questions: List<Question>)
