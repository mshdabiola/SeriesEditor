package com.mshdabiola.serieseditor.di

import com.mshdabiola.composeinstruction.ciModule
import com.mshdabiola.composequestion.cqModule
import com.mshdabiola.composesubject.subjectModule
import com.mshdabiola.composetopic.ctModule
import com.mshdabiola.data.di.dataModule
import com.mshdabiola.instructions.instructionsModule
import com.mshdabiola.main.mainModule
import com.mshdabiola.questions.questionsModule
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.setting.settingModule
import com.mshdabiola.composeexam.composeExamModule
import com.mshdabiola.topics.topicModule
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule, mainModule, subjectModule, composeExamModule, settingModule,
        cqModule, ciModule, questionsModule, instructionsModule, ctModule, topicModule
    )
    viewModelOf(::MainAppViewModel)
}
